package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public interface Reactor {
    String uuid();
    ReactorStatus status();
    ReactorType type();
    Mono<Reactor> start();
    Reactor startAwait();
    void shutdown();
}
