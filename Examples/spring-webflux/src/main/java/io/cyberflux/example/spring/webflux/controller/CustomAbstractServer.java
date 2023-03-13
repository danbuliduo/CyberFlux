package io.cyberflux.example.spring.webflux.controller;

import org.springframework.stereotype.Component;

import io.cyberflux.meta.reactor.AbstractReactor;
import io.cyberflux.meta.reactor.ProtocolType;
import io.cyberflux.meta.reactor.Reactor;
import reactor.core.publisher.Mono;

@Component
public class CustomAbstractServer extends AbstractReactor {

    public CustomAbstractServer() {
        super(ProtocolType.UNKNOWN);
    }

    @Override
    public void shutdown() {

    }

    @Override
    public Mono<Reactor> start() {
        System.out.println(this.getClass());
        return Mono.empty();
    }
}
