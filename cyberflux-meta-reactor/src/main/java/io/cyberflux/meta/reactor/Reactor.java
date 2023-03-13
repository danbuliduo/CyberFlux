package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public interface Reactor {
    String uuid();
    ProtocolType protocolType();
    Mono<Reactor> start();
    Reactor startAwait();
    void shutdown();
}
