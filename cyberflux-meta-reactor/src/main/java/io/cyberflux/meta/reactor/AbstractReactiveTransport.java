package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

public abstract class AbstractReactiveTransport implements ReactiveTransport {
    public abstract Mono<ReactiveTransport> start();
    public abstract Mono<ReactiveTransport> bind();
    public abstract Mono<Void> dispose();
}
