package io.cyberflux.spring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import io.cyberflux.meta.reactor.AbstractClusterNode;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.node.engine.CyberFluxNodeEngine;
import io.cyberflux.node.engine.container.CyberFluxReactorGroup;
import reactor.core.publisher.Flux;

public class CyberFluxSpringEngine extends AbstractClusterNode implements CyberFluxNodeEngine {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxSpringEngine.class);
    private static final CyberFluxReactorGroup reactorGroup = CyberFluxReactorGroup.INSTANCE;

    static {
        log.info("<= Welcome To Use EngineCore: SPRING - 昭节 - =>");
    }

    public CyberFluxSpringEngine(ApplicationContext context) {
        super(context.getApplicationName());
        System.out.println("@@"+ context.getApplicationName());
        System.out.println("@@" + context.getId());
        Flux.fromArray(context.getBeanNamesForType(Reactor.class))
            .flatMap(name -> Flux.just(context.getBean(name)))
            .cast(Reactor.class)
            .doOnNext(reactorGroup::saveReactor)
            .subscribe(this::startReactor);
    }

    public static CyberFluxSpringEngine run(ApplicationContext context) {
        return new CyberFluxSpringEngine(context);
    }

    @Override
    public Flux<Reactor> findReactor() {
        return reactorGroup.findReactor();
    }

    public void saveReactor(Reactor reactor) {
        reactorGroup.saveReactor(reactor);
    }

    public void startReactor(Reactor reactor) {
        reactor.startAwait();
        log.info("Reactor ==> {} - type:{}, uuid:{}", reactor.getClass(), reactor.protocolType(), reactor.uuid());
    }
}
