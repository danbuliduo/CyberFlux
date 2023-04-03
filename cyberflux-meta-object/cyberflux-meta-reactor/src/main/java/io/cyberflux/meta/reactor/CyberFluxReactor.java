package io.cyberflux.meta.reactor;

import io.cyberflux.meta.medium.MediumType;
import io.cyberflux.meta.medium.MediumStatus;
import reactor.core.publisher.Mono;

public interface CyberFluxReactor {
    String uuid();
    MediumStatus status();
    MediumType type();
    Mono<CyberFluxReactor> start();
    CyberFluxReactor startAwait();
    void shutdown();
}
