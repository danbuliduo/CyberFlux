package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

import java.util.UUID;

public abstract class AbstractReactor implements Reactor {
    protected String uuid;
    protected ProtocolType protocolType;

    public AbstractReactor() {
        this(ProtocolType.UNKNOWN);
    }

    public AbstractReactor(ProtocolType protocolType) {
        this(UUID.randomUUID().toString(), protocolType);
    }

    public AbstractReactor(String uuid, ProtocolType protocolType) {
        this.uuid = uuid;
        this.protocolType = protocolType;
    }

    public abstract Mono<Reactor> start();

    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public ProtocolType protocolType() {
        return protocolType;
    }

    @Override
    public Reactor startAwait() {
        return start().block();
    }
}