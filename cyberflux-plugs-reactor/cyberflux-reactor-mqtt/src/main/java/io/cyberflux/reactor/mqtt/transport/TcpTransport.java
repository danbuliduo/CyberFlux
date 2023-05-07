package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.reactor.mqtt.channel.MqttChannel;
import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
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
	public Mono<DisposableServer> overTransport(ContextView context) {
		MqttChannelContext channelContext = context.get(MqttChannelContext.class);
		return TcpServer.create()
			.port(config.getPort())
			.childOption(ChannelOption.TCP_NODELAY, true)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.childOption(ChannelOption.SO_REUSEADDR, true)
			.option(ChannelOption.SO_REUSEADDR, true)
			.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
			.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
			.doOnConnection(conn -> {
				conn.addHandlerFirst(MqttEncoder.INSTANCE)
                    .addHandlerFirst(new MqttDecoder());
				this.context.applyChannel(
					new MqttChannel(conn, channelContext.getAcknowledgementManager())
				);
			}).bind().cast(DisposableServer.class);
	}
}
