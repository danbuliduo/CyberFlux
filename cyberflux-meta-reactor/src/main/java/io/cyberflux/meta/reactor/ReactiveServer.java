package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public interface ReactiveServer {
    String uuid();
    ProtocolType protocolType();
    Mono<ReactiveServer> start();
    ReactiveServer startAwait();
    void shutdown();
}
