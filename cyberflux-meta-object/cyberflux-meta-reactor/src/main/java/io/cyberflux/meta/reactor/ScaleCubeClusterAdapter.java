package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberClusterMessage;
import io.scalecube.reactor.RetryNonSerializedEmitFailureHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public abstract class ScaleCubeClusterAdapter implements CyberClusterHandler {
	private final Sinks.Many<CyberClusterMessage> messageMany;

	public ScaleCubeClusterAdapter() {
		this.messageMany = Sinks.many().multicast().onBackpressureBuffer();
	}

	public void spreadMessage(CyberClusterMessage message) {
		messageMany.emitNext(message, new RetryNonSerializedEmitFailureHandler());
	}

	@Override
	public Flux<CyberClusterMessage> messageProducer() {
		return messageMany.asFlux();
	}
}
