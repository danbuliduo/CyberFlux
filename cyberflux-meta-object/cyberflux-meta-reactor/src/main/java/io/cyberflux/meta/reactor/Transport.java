package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public interface Transport {
    Mono<Transport> start();
    Mono<Void> dispose();
}
