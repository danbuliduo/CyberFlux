package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

import java.util.UUID;

public abstract class AbstractReactiveServer implements ReactiveServer {
    private String uuid;
    private ProtocolType protocolType;

    public AbstractReactiveServer() {
        this(ProtocolType.UNKNOWN);
    }

    public AbstractReactiveServer(ProtocolType protocolType) {
        this(UUID.randomUUID().toString(), protocolType);
    }

    public AbstractReactiveServer(String uuid, ProtocolType protocolType) {
        this.uuid = uuid;
        this.protocolType = protocolType;
    }

    public abstract Mono<ReactiveServer> start();

    @Override
    public ProtocolType protocolType() {
        return protocolType;
    }

    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public ReactiveServer startAwait() {
        return start().block();
    }
}