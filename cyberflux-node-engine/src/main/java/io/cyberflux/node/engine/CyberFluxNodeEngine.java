package io.cyberflux.node.engine;

import reactor.core.publisher.Mono;

public class CyberFluxNodeEngine implements NodeEngine {
    @Override
    public Mono<NodeEngine> start() {
        return Mono.empty();
    }

    @Override
    public void startAwait() {
        start().block();
    }

    @Override
    public void shutdown() {

    }
}
