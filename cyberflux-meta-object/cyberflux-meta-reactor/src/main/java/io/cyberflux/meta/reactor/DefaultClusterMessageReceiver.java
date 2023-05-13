package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberClusterMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public abstract class DefaultClusterMessageReceiver implements CyberClusterMessageReceiver {

	protected Sinks.Many<CyberClusterMessage> messageMany;

	public DefaultClusterMessageReceiver() {
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
