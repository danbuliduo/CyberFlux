package io.cyberflux.node.engine;

import io.cyberflux.node.engine.huaxu.annotation.CyberInject;
import io.cyberflux.node.engine.huaxu.annotation.CyberMetaObject;
import jakarta.annotation.PostConstruct;

@CyberMetaObject
public class TestComponent {
    @CyberInject
    public CustomReactor aserver;

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
    }
}
