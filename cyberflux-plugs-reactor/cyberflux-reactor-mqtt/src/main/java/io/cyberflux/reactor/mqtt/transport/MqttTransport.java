package io.cyberflux.reactor.mqtt.transport;


import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import io.cyberflux.meta.reactor.TemplateTransport;
import io.cyberflux.meta.reactor.CyberTransport;
import io.cyberflux.reactor.mqtt.channel.MqttChannelHandlerContext;

public abstract class MqttTransport<D extends Disposable, T extends MqttTransportConfig>
	 	extends TemplateTransport<D, T> {

	protected MqttChannelHandlerContext context;

	public abstract Mono<? extends D> overTransport(ContextView context);

	private void bindTransport(D disposable) {
		this.disposable = disposable;
	}

	private Context bindContext(Context ctx) {
		context = new MqttChannelHandlerContext();
		return ctx.put(MqttChannelHandlerContext.class, context);
	}

    public MqttTransport(T config) {
		super(config);
    }

	public void setContext(MqttChannelHandlerContext context) {
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