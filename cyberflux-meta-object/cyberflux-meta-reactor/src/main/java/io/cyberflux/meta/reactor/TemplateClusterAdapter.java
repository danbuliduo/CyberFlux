package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberClusterMessage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public abstract class TemplateClusterAdapter implements CyberClusterHandler {
	protected Sinks.Many<CyberClusterMessage> messageMany;

	public TemplateClusterAdapter() {
		this.messageMany = Sinks.many().multicast().onBackpressureBuffer();
	}

	public void spreadMessage(CyberClusterMessage message) {
		messageMany.tryEmitNext(message);
	}

	@Override
	public Flux<CyberClusterMessage> messageProducer() {
		return messageMany.asFlux();
	}
}
