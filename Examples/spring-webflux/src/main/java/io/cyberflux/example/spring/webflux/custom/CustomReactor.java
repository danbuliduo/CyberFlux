package io.cyberflux.example.spring.webflux.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.cyberflux.meta.medium.MediumType;
import io.cyberflux.meta.reactor.CyberFluxAbstractReactor;
import io.cyberflux.meta.reactor.CyberFluxReactor;
import reactor.core.publisher.Mono;

@Component
public class CustomReactor extends CyberFluxAbstractReactor {
    private static final Logger log = LoggerFactory.getLogger(CustomReactor.class);

    public CustomReactor() {
        super(MediumType.CUSTOM);
    }

    @Override
    public void shutdown() {
        log.info("shutdown method is called");
    }

    @Override
    public Mono<CyberFluxReactor> start() {
        log.info("start method is called");
        return Mono.empty().thenReturn(this);
    }
}
