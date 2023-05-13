package io.cyberflux.reactor.mqtt.transport;


import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import reactor.util.context.ContextView;
import io.cyberflux.meta.reactor.transport.CyberTransport;
import io.cyberflux.meta.reactor.transport.AbstractTransport;

import java.util.Optional;

import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;

public abstract class MqttTransport<D extends Disposable, T extends MqttTransportConfig>
		extends AbstractTransport<D, T> {

	protected MqttChannelContext context;

	public abstract Mono<D> overTransport(ContextView context);

	public MqttTransport(T config) {
		super(config);
	}

	private void bindTransport(D disposable) {
		this.disposable = disposable;
	}

	private Context bindContext(Context ctx) {
		context = Optional.ofNullable(context).orElse(MqttChannelContext.INSTANCE);
		return ctx.put(MqttChannelContext.class, context);
	}

	public void setContext(MqttChannelContext context) {
		this.context = context;
	}

	public MqttChannelContext getContext() {
		return context;
	}

    @Override
    public Mono<CyberTransport> start() {
        return Mono.deferContextual(this::overTransport)
				.doOnNext(this::bindTransport)
				.contextWrite(this::bindContext)
				.thenReturn(this);
    }
}