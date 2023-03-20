package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public abstract class CyberFluxAbstractTransport implements CyberFluxTransport {
    public abstract Mono<CyberFluxTransport> start();
    public abstract Mono<Void> dispose();
}
