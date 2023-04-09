package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.reactor.mqtt.channel.MqttChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;
import reactor.util.context.ContextView;

public final class TcpTransport extends MqttTransport<DisposableServer, TcpTransportConfig> {

	public TcpTransport() {
		this(new TcpTransportConfig());
	}

	public TcpTransport(TcpTransportConfig config) {
		super(config);
	}

	@Override
	public Mono<? extends DisposableServer> overTransport(ContextView context) {
		return TcpServer.create()
			.port(config.getPort())
			.doOnConnection(conn -> {
				conn.addHandlerFirst(MqttEncoder.INSTANCE)
                    .addHandlerFirst(new MqttDecoder());
				this.context.applyChannel(new MqttChannel(conn));
			}).bind();
	}
}