package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberClusterMessage;
import reactor.core.publisher.Mono;

public interface CyberClusterMessagePublisher {
	Mono<Void> publish(CyberClusterMessage message);
}
