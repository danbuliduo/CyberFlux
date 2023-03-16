package io.cyberflux.example.spring.webflux.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.cyberflux.meta.reactor.AbstractReactor;
import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.Reactor;
import reactor.core.publisher.Mono;

@Component
public class CustomReactor extends AbstractReactor {
    private static final Logger log = LoggerFactory.getLogger(CustomReactor.class);

    public CustomReactor() {
        super(ReactorType.UNKNOWN);
    }

    @Override
    public void shutdown() {
        log.info("shutdown method is called");
    }

    @Override
    public Mono<Reactor> start() {
        log.info("start method is called");
        return Mono.empty().thenReturn(this);
    }
}
