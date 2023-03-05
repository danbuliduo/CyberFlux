package io.cyberflux.node.engine.core;

import java.util.HashMap;
import java.util.Map;

import io.cyberflux.meta.reactor.ReactiveServer;

public class CyberFluxServerBeanFactory {
    public final Map<String, ReactiveServer> serverMap = new HashMap<>();
}
