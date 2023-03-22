package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

import java.util.UUID;

public abstract class CyberFluxAbstractReactor implements CyberFluxReactor {
    protected String uuid;
    protected ReactorStatus status;
    protected ReactorType type;

    public CyberFluxAbstractReactor() {
        this(ReactorType.EMPTY);
    }

    public CyberFluxAbstractReactor(ReactorType type) {
        this(UUID.randomUUID().toString(), type);
    }

    public CyberFluxAbstractReactor(String uuid, ReactorType type) {
        this.uuid = uuid;
        this.type = type;
        this.status = ReactorStatus.INVALID;
    }

    public abstract Mono<CyberFluxReactor> start();

    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public ReactorStatus status() {
        return status;
    }

    @Override
    public ReactorType type() {
        return type;
    }

    @Override
    public CyberFluxReactor startAwait() {
        return start().block();
    }
}