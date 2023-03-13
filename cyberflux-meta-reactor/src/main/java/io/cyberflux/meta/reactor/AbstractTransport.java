package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public abstract class AbstractTransport implements Transport {
    public abstract Mono<Transport> start();
    public abstract Mono<Transport> bind();
    public abstract Mono<Void> dispose();
}
