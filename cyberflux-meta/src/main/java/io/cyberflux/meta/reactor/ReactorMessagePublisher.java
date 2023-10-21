package io.cyberflux.meta.reactor;

import io.cyberflux.meta.cluster.ClusterMessage;
import reactor.core.publisher.Mono;

public interface ReactorMessagePublisher {
    Mono<Void> publish(ClusterMessage message);
}
