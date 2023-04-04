package io.cyberflux.reactor.mqtt.transport;


import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import java.util.Optional;

import io.cyberflux.meta.reactor.AbstractTransport;
import io.cyberflux.meta.reactor.Transport;
import io.cyberflux.reactor.mqtt.channel.MqttChannelHandlerContext;

public abstract class MqttTransport<D extends Disposable, T extends MqttTransportConfig> extends AbstractTransport {

	protected D disposable;
	protected T config;
	protected MqttChannelHandlerContext context;

	public abstract Mono<? extends Disposable> overTransport(ContextView context);

	@SuppressWarnings("unchecked")
	private void bindTransport(Disposable disposable) {
		this.disposable = (D) disposable;
	}

	private Context bindContext(Context ctx) {
		return ctx.put(MqttChannelHandlerContext.class, context);
	}

    public MqttTransport(T config) {
		this.config = config;
        Runtime.getRuntime().addShutdownHook(
            new Thread(() -> Optional.ofNullable(disposable).ifPresent(D::dispose))
        );
    }

	public void setContext(MqttChannelHandlerContext context) {
		this.context = context;
	}

    @Override
    public Mono<Transport> start() {
        return Mono.deferContextual(this::overTransport)
				.doOnNext(this::bindTransport)
				.contextWrite(this::bindContext)
				.thenReturn(this);
    }

    @Override
    public Mono<Void> dispose() {
        return Mono.fromRunnable(() -> {
            disposable.dispose();
        });
    }
}