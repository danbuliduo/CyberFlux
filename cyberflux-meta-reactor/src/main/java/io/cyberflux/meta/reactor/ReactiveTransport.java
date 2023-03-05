package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public interface ReactiveTransport {
    Mono<ReactiveTransport> start();
    Mono<ReactiveTransport> bind();
    Mono<Void> dispose();
}
