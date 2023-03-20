package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public interface CyberFluxReactor {
    String uuid();
    ReactorStatus status();
    ReactorType type();
    Mono<CyberFluxReactor> start();
    CyberFluxReactor startAwait();
    void shutdown();
}
