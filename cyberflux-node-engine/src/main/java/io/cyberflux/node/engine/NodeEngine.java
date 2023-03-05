package io.cyberflux.node.engine;

import reactor.core.publisher.Mono;

public interface NodeEngine {
    Mono<NodeEngine> start();
    void startAwait();
    void shutdown();
}
