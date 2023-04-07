package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.reactor.mqtt.channel.MqttChannel;
import io.cyberflux.reactor.mqtt.codec.MqttWebSocketCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;
import reactor.util.context.ContextView;

public final class WebSocketTransport extends MqttTransport<DisposableServer, WebSocketTransportConfig> {

	public WebSocketTransport() {
		this(new WebSocketTransportConfig());
	}

	public WebSocketTransport(WebSocketTransportConfig config) {
		super(config);
	}

	@Override
	public Mono<? extends DisposableServer> overTransport(ContextView context) {
		return TcpServer.create()
				.port(config.getPort())
				.doOnConnection(conn -> {
					conn.addHandlerLast(new HttpServerCodec())
						.addHandlerLast(new HttpObjectAggregator(65536))
						.addHandlerLast(new HttpContentCompressor())
						.addHandlerLast(new WebSocketServerProtocolHandler(config.path, "mqtt", true, 65536))
						.addHandlerLast(new MqttWebSocketCodec())
						.addHandlerLast(MqttEncoder.INSTANCE)
						.addHandlerLast(new MqttDecoder());
					this.context.applyChannel(new MqttChannel(conn));
				}).bind();
	}
}
