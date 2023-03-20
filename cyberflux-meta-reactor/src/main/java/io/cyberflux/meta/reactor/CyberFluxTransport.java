package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public interface CyberFluxTransport {
    Mono<CyberFluxTransport> start();
    Mono<Void> dispose();
}
