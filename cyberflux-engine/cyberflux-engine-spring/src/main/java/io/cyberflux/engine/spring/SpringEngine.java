package io.cyberflux.engine.spring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.engine.spring.config.SpringEngineProperties;
import io.cyberflux.engine.core.TemplateEngine;
import reactor.core.publisher.Flux;

public class SpringEngine extends TemplateEngine {

    private static final Logger log = LoggerFactory.getLogger(SpringEngine.class);

    static {
        log.info("<= Welcome To Use EngineCore: SPRING - 昭节 - =>");
    }

    public static SpringEngine run(ApplicationContext context, SpringEngineProperties properties) {
        return new SpringEngine(context, properties);
    }

    public SpringEngine(ApplicationContext context, SpringEngineProperties properties) {
		super(properties);
        Flux.fromArray(context.getBeanNamesForType(Reactor.class))
            .map(context::getBean)
            .cast(Reactor.class)
            .doOnNext(this::appendReactor)
            .subscribe();
        this.startReactor();
    }
}
