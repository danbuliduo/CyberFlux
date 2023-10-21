package io.cyberflux.meta.reactor;

import io.cyberflux.meta.cluster.ClusterMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public abstract class ReactorMessageReceiverAdapter implements ReactorMessageReceiver {

	private final Sinks.Many<ClusterMessage> messageMany;

	public ReactorMessageReceiverAdapter() {
		this.messageMany = Sinks.many().multicast().onBackpressureBuffer();
	}

	public void emitMessage(ClusterMessage message) {
		messageMany.tryEmitNext(message);
	}

	@Override
	public Flux<ClusterMessage> receive() {
		return messageMany.asFlux();
	}
}
