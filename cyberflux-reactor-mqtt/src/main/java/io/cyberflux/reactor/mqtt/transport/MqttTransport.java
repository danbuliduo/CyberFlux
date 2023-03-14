package io.cyberflux.reactor.mqtt.transport;


import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;
import reactor.util.context.ContextView;

import java.util.Optional;

import io.cyberflux.meta.reactor.AbstractTransport;
import io.cyberflux.meta.reactor.Transport;
import io.cyberflux.reactor.mqtt.channel.MqttChannel;
import io.cyberflux.reactor.mqtt.channel.MqttChannelInboundHandler;
import io.cyberflux.reactor.mqtt.codec.MqttWebSocketCodec;
import io.cyberflux.reactor.mqtt.config.MqttTransportConfig;
import io.cyberflux.reactor.mqtt.channel.AutoMqttChannelInboundHandler;

import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;


public class MqttTransport extends AbstractTransport {
    private DisposableServer server;
    private MqttChannelInboundHandler handler = new AutoMqttChannelInboundHandler(Schedulers.boundedElastic());

    public MqttTransport() {
        Runtime.getRuntime().addShutdownHook(
            new Thread(() -> Optional.ofNullable(server).ifPresent(DisposableServer::dispose))
        );
    }

    @Override
    public Mono<Transport> start() {
        return Mono.deferContextual(ctx-> Mono.just(this.overProtocol(ctx)))
                .flatMap(server -> server.bind().cast(DisposableServer.class))
                .thenReturn(this);
    }

    @Override
    public Mono<Void> dispose() {
        return Mono.fromRunnable(() -> {
            server.dispose();
        });
    }

    public void bind(DisposableServer server) {
        this.server = server;
    }

    private TcpServer overProtocol(ContextView context) {
        MqttTransportConfig config = context.get(MqttTransportConfig.class);
        return switch (config.getType()) {
            case TCP       -> this.overTcp(config);
            case WEBSOCKET -> this.overWebSocket(config);
            default        -> this.overTcp(config);
        };
    }

    private TcpServer overTcp(MqttTransportConfig config) {
        return TcpServer.create().port(config.getPort())
                .doOnConnection(conn -> {
                    conn.addHandlerFirst(MqttEncoder.INSTANCE)
                        .addHandlerFirst(new MqttDecoder());
                    handler.channelRegister(new MqttChannel(conn));
                });
    }

    private TcpServer overWebSocket(MqttTransportConfig config) {
        return TcpServer.create().port(config.getPort())
                .doOnConnection(conn -> {
                    conn.addHandlerLast(new HttpServerCodec())
                        .addHandlerLast(new HttpObjectAggregator(65536))
                        .addHandlerLast(new HttpContentCompressor())
                        .addHandlerLast(new WebSocketServerProtocolHandler("/mqtt", "mqtt", true, 65536))
                        .addHandlerLast(new MqttWebSocketCodec())
                        .addHandlerLast(MqttEncoder.INSTANCE)
                        .addHandlerLast(new MqttDecoder());
                    handler.channelRegister(new MqttChannel(conn));
                });
    }
}