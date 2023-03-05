package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public interface ReactiveServer {
    ProtocolType protocolType();
    Mono<ReactiveServer> start();
    ReactiveServer startAwait();
    void shutdown();
}
