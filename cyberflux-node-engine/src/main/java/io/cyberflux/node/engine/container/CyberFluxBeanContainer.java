package io.cyberflux.node.engine.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import reactor.core.publisher.Flux;

public class CyberFluxBeanContainer {
    private final Map<String, Object> beans = new ConcurrentHashMap<>();
    public static final CyberFluxBeanContainer INSTANCE = new CyberFluxBeanContainer();

    public boolean contains(String injectName) {
        return beans.containsKey(injectName);
    }

    public Object findBean(String injectName) {
        return beans.get(injectName);
    }

    public void saveBean(String injectName, Object bean) {
        beans.put(injectName, bean);
    }

    public <T> Flux<T> beanFlux(Class<T> clasz) {
        return Flux.fromIterable(beans.values()).filter(
            item -> clasz.isAssignableFrom(item.getClass())
        ).cast(clasz);
    }
}