package io.cyberflux.spring.engine;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.node.engine.core.CyberFluxNodeEngine;
import io.cyberflux.node.engine.core.CyberFluxTemplateEngine;
import reactor.core.publisher.Flux;

public class CyberFluxSpringEngine extends CyberFluxTemplateEngine implements CyberFluxNodeEngine {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxSpringEngine.class);

    static {
        log.info("<= Welcome To Use EngineCore: SPRING - 昭节 - =>");
    }

    public static CyberFluxSpringEngine run(ApplicationContext context) {
        return new CyberFluxSpringEngine(context);
    }

    public CyberFluxSpringEngine(ApplicationContext context) {
        super(context.getApplicationName());
        Flux.fromArray(context.getBeanNamesForType(Reactor.class))
            .flatMap(name -> Flux.just(context.getBean(name)))
            .cast(Reactor.class)
            .doOnNext(reactorGroup::appendReactor)
            .subscribe(this::startReactor);
    }
}
