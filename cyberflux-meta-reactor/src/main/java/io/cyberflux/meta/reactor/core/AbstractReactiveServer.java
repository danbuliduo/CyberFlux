package io.cyberflux.meta.reactor.core;

import reactor.core.publisher.Mono;
import io.cyberflux.meta.reactor.ProtocolType;
import io.cyberflux.meta.reactor.ReactiveServer;

public abstract class AbstractReactiveServer implements ReactiveServer {
    private ProtocolType protocolType = ProtocolType.UNKNOWN;

    public AbstractReactiveServer(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    public abstract Mono<ReactiveServer> start();

    @Override
    public ProtocolType protocolType() {
        return protocolType;
    }

    @Override
    public ReactiveServer startAwait() {
        return start().block();
    }
}