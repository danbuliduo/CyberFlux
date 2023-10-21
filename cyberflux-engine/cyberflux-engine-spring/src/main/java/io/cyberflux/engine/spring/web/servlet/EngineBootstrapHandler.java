package io.cyberflux.engine.spring.web.servlet;

import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.event.EventListener;

import io.cyberflux.engine.spring.SpringEngine;

public class EngineBootstrapHandler {

    private final SpringEngine springEngine;

    public EngineBootstrapHandler(SpringEngine springEngine) {
        this.springEngine = springEngine;
    }

    @EventListener
    public void onApplicationEvent(final ServletWebServerInitializedEvent event) {
        this.springEngine.context().config().setApiPort(event.getWebServer().getPort());
        springEngine.doRegisterGateway();
    }
}
