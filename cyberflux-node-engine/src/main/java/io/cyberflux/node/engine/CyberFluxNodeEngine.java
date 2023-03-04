package io.cyberflux.node.engine;

import reactor.core.publisher.Mono;

public class CyberFluxNodeEngine {
    public Mono<CyberFluxNodeEngine> start() {
        return Mono.empty();
    }
    public void startAwait() {
        start().block();
    }
    public void shutdown() {

    }
}
