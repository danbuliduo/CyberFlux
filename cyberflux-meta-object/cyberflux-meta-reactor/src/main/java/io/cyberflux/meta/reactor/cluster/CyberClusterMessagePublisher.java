package io.cyberflux.meta.reactor.cluster;

import io.cyberflux.meta.data.cluster.CyberClusterMessage;
import reactor.core.publisher.Mono;

public interface CyberClusterMessagePublisher {
	Mono<Void> publish(CyberClusterMessage message);
}
