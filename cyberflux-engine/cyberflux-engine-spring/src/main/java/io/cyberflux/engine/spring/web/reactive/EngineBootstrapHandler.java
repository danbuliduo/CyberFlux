package io.cyberflux.engine.spring.web.reactive;

import org.springframework.boot.web.reactive.context.ReactiveWebServerInitializedEvent;
import org.springframework.context.event.EventListener;

import io.cyberflux.engine.spring.SpringEngine;

public class EngineBootstrapHandler {

    private final SpringEngine springEngine;

    public EngineBootstrapHandler(SpringEngine springEngine) {
        this.springEngine = springEngine;
    }

    @EventListener
    public void onApplicationEvent(final ReactiveWebServerInitializedEvent event) {
        this.springEngine.context().config().setApiPort(event.getWebServer().getPort());
        springEngine.doRegisterGateway();
    }
}
