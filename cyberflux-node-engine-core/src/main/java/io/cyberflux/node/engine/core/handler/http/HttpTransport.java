package io.cyberflux.node.engine.core.handler.http;

import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.transport.AbstractTransport;
import io.cyberflux.meta.reactor.transport.CyberTransport;
import io.cyberflux.meta.reactor.transport.TransportConfig;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.handler.codec.json.JsonObjectDecoder;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.util.context.ContextView;

public class HttpTransport extends AbstractTransport<DisposableServer, HttpTransportConfig>   {



	public HttpTransport(HttpTransportConfig config) {
		super(CyberType.HTTP, config);
	}

	private void bindTransport(DisposableServer disposable) {
		this.disposable = disposable;
	}

	public Mono<DisposableServer> overTransport(ContextView contextView) {
		return HttpServer.create().port(17777)
				.doOnConnection(conn -> conn.addHandler(new JsonObjectDecoder()))
				.route(new HttpRouterAcceptor())
				.accessLog(true)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.SO_REUSEADDR, true)
				.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.wiretap(true)
				.bind()
				.cast(DisposableServer.class);
	}

	@Override
	public Mono<CyberTransport> start() {
		return Mono.deferContextual(this::overTransport)
				.doOnNext(this::bindTransport)
				.thenReturn(this);
	}

}
