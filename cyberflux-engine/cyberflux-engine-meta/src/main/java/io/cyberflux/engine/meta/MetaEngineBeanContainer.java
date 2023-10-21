package io.cyberflux.engine.meta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import reactor.core.publisher.Flux;


public class MetaEngineBeanContainer {
    private final Map<String, Object> beans = new ConcurrentHashMap<>();

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
            obj -> clasz.isAssignableFrom(obj.getClass())
        ).cast(clasz);
    }
}