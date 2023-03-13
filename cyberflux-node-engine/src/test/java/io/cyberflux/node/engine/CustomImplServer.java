package io.cyberflux.node.engine;

import java.util.UUID;

import io.cyberflux.meta.reactor.ProtocolType;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.node.engine.annotation.CyberReactor;
import reactor.core.publisher.Mono;


@CyberReactor
public class CustomImplServer implements Reactor {
    private String uuid = UUID.randomUUID().toString();
    @Override
    public ProtocolType protocolType() {
        return ProtocolType.UNKNOWN;
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
}
