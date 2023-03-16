package io.cyberflux.node.engine;

import java.util.UUID;

import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.meta.reactor.ReactorStatus;
import io.cyberflux.node.engine.annotation.CyberReactor;
import reactor.core.publisher.Mono;


@CyberReactor
public class CustomReactor implements Reactor {
    private String uuid = UUID.randomUUID().toString();

    @Override
    public ReactorType type() {
        return ReactorType.CUSTOM;
    }

    @Override
    public Mono<Reactor> start() {
        System.out.println(this.getClass());
        return Mono.empty();
    }

    @Override
    public Reactor startAwait() {
        return start().block();
    }

    @Override
    public void shutdown() {
    }

    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public ReactorStatus status() {
       return ReactorStatus.OPERATION;
    }
}
