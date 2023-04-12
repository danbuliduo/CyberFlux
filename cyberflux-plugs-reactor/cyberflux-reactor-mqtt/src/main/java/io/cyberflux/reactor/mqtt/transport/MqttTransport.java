package io.cyberflux.reactor.mqtt.transport;


import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import io.cyberflux.meta.reactor.TemplateTransport;
import io.cyberflux.meta.reactor.CyberTransport;
import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;

public abstract class MqttTransport<D extends Disposable, T extends MqttTransportConfig>
		extends TemplateTransport<D, T> {

	protected MqttChannelContext context;

	public abstract Mono<D> overTransport(ContextView context);

	private void bindTransport(D disposable) {
		this.disposable = disposable;
	}

	private Context bindContext(Context ctx) {
		context = new MqttChannelContext();
		return ctx.put(MqttChannelContext.class, context);
	}

    public MqttTransport(T config) {
		super(config);
    }

	public void setContext(MqttChannelContext context) {
		this.context = context;
	}

    @Override
    public Mono<CyberTransport> start() {
        return Mono.deferContextual(this::overTransport)
				.doOnNext(this::bindTransport)
				.contextWrite(this::bindContext)
				.thenReturn(this);
    }
}