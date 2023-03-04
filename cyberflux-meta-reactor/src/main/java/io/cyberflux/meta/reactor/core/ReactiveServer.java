package io.cyberflux.meta.reactor.core;

import reactor.core.publisher.Mono;

public interface ReactiveServer {
    Mono<ReactiveServer> start();
    ReactiveServer startAwait();
    void shutdown();
}
