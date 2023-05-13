package io.cyberflux.meta.reactor.cluster;

import io.cyberflux.meta.data.cluster.CyberClusterMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public abstract class AbstractClusterMessageReceiver implements CyberClusterMessageReceiver {

	private final Sinks.Many<CyberClusterMessage> messageMany;

	public AbstractClusterMessageReceiver() {
		this.messageMany = Sinks.many().multicast().onBackpressureBuffer();
	}

	public void emitMessage(CyberClusterMessage message) {
		messageMany.tryEmitNext(message);
	}

	@Override
	public Flux<CyberClusterMessage> receive() {
		return messageMany.asFlux();
	}
}
