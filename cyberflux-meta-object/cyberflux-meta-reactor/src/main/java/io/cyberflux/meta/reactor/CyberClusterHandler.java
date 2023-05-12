package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberClusterMessage;
import reactor.core.publisher.Flux;

public interface CyberClusterHandler {
	void messageConsumer(CyberClusterMessage message);
	Flux<CyberClusterMessage> messageProducer();
}
