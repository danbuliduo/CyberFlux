package io.cyberflux.node.engine;

import io.cyberflux.node.engine.annotation.CyberInject;
import io.cyberflux.node.engine.annotation.CyberReactor;
import jakarta.annotation.PostConstruct;

@CyberReactor
public class TestComponent {
    @CyberInject
    public CustomImplServer aserver;

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
    }
}
