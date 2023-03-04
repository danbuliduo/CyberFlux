package io.cyberflux.meta.reactor.core;

import reactor.core.publisher.Mono;

public interface ReactiveTransport {
    Mono<ReactiveTransport> start();
    Mono<Void> dispose();
}
