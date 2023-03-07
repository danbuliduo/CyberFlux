package io.cyberflux.spring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import io.cyberflux.meta.reactor.ReactiveServer;
import io.cyberflux.node.engine.CyberFluxNodeEngine;
import io.cyberflux.node.engine.container.CyberFluxReactorGroup;
import reactor.core.publisher.Flux;

public class CyberFluxSpringEngine implements CyberFluxNodeEngine {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxSpringEngine.class);
    private final CyberFluxReactorGroup reactorGroup = CyberFluxReactorGroup.INSTANCE;

    static {
        log.info("<= Welcome To Use EngineCore: SPRING - 昭节 - =>");
    }

    public CyberFluxSpringEngine(ApplicationContext context) {
        Flux.fromArray(context.getBeanNamesForType(ReactiveServer.class))
            .flatMap(name -> Flux.just(context.getBean(name)))
            .cast(ReactiveServer.class)
            .doOnNext(reactorGroup::saveReactor)
            .subscribe(this::startReactor);
    }

    public static CyberFluxSpringEngine run(ApplicationContext context) {
        return new CyberFluxSpringEngine(context);
    }

    public void saveReactor(ReactiveServer reactor) {
        reactorGroup.saveReactor(reactor);
    }

    public void startReactor(ReactiveServer reactor) {
        reactor.startAwait();
        log.info("Reactor ==> {} - type:{}, uuid:{}", reactor.getClass(), reactor.protocolType(), reactor.uuid());
    }
}
