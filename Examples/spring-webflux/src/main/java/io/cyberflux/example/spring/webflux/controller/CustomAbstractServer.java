package io.cyberflux.example.spring.webflux.controller;

import org.springframework.stereotype.Component;

import io.cyberflux.meta.reactor.ProtocolType;
import io.cyberflux.meta.reactor.ReactiveServer;
import io.cyberflux.meta.reactor.core.AbstractReactiveServer;
import reactor.core.publisher.Mono;

@Component
public class CustomAbstractServer extends AbstractReactiveServer {

    public CustomAbstractServer() {
        super(ProtocolType.UNKNOWN);
    }

    @Override
    public void shutdown() {

    }

    @Override
    public Mono<ReactiveServer> start() {
        System.out.println(this.getClass());
        return Mono.empty();
    }
}
