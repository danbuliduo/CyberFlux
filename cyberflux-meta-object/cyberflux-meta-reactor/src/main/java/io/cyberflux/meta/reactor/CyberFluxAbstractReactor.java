package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

import java.util.UUID;

import io.cyberflux.meta.medium.MediumType;
import io.cyberflux.meta.medium.MediumStatus;

public abstract class CyberFluxAbstractReactor implements CyberFluxReactor {
    protected String uuid;
    protected MediumStatus status;
    protected MediumType type;

    public CyberFluxAbstractReactor() {
        this(MediumType.EMPTY);
    }

    public CyberFluxAbstractReactor(MediumType type) {
        this(UUID.randomUUID().toString(), type);
    }

    public CyberFluxAbstractReactor(String uuid, MediumType type) {
        this.uuid = uuid;
        this.type = type;
        this.status = MediumStatus.INVALID;
    }

    public abstract Mono<CyberFluxReactor> start();

    @Override
    public final String uuid() {
        return uuid;
    }

    @Override
    public final MediumStatus status() {
        return status;
    }

    @Override
    public final MediumType type() {
        return type;
    }

    @Override
    public CyberFluxReactor startAwait() {
        return start().block();
    }
}