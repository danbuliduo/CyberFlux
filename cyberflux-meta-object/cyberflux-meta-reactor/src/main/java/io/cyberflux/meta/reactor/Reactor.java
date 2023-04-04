package io.cyberflux.meta.reactor;

import io.cyberflux.meta.medium.MediumType;
import io.cyberflux.meta.medium.MediumStatus;
import reactor.core.publisher.Mono;

public interface Reactor {
    String uuid();
    MediumStatus status();
    MediumType type();
    Mono<Reactor> start();
	Mono<Void> stop();
    Reactor startAwait();
    void shutdown();
}
