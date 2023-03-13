package io.cyberflux.reactor.mqtt.transport;


import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;
import reactor.util.context.ContextView;


import io.cyberflux.meta.reactor.AbstractTransport;
import io.cyberflux.meta.reactor.Transport;
import io.cyberflux.reactor.mqtt.channel.MqttChannel;
import io.cyberflux.reactor.mqtt.channel.MqttChannelInboundHandler;
import io.cyberflux.reactor.mqtt.channel.AutoMqttChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;

public class MqttTransport extends AbstractTransport {
    private DisposableServer server;
    private MqttChannelInboundHandler handler = new AutoMqttChannelInboundHandler(Schedulers.boundedElastic());

    @Override
    public Mono<Transport> start() {
        return Mono.deferContextual(ctx-> Mono.just(this.overTcp(ctx)))
                .flatMap(server -> server.bind().cast(DisposableServer.class))
                .thenReturn(this);
    }

    public void bind(DisposableServer server) {
        this.server = server;
    }

    @Override
    public Mono<Void> dispose() {
        return Mono.fromRunnable(() -> {
            server.dispose();
        });
    }

    @Override
    public Mono<Transport> bind() {
        throw new UnsupportedOperationException("Unimplemented method 'bind'");
    }

    private TcpServer overTcp(ContextView context) {
        return TcpServer.create().port(1883)
                .doOnConnection(conn -> {
                    conn.addHandlerFirst(MqttEncoder.INSTANCE)
                        .addHandlerFirst(new MqttDecoder());
                    handler.channelRegister(new MqttChannel(conn));
                });
    }
}