package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

import java.util.UUID;

public abstract class AbstractReactor implements Reactor {
    protected String uuid;
    protected ReactorStatus status;
    protected ReactorType type;

    public AbstractReactor() {
        this(ReactorType.UNKNOWN);
    }

    public AbstractReactor(ReactorType type) {
        this(UUID.randomUUID().toString(), type);
    }

    public AbstractReactor(String uuid, ReactorType type) {
        this.uuid = uuid;
        this.type = type;
        this.status = ReactorStatus.INVALID;
    }

    public abstract Mono<Reactor> start();

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
    public Reactor startAwait() {
        return start().block();
    }
}