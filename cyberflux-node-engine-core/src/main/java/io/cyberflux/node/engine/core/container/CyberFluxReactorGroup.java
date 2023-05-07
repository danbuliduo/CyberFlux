package io.cyberflux.node.engine.core.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.CyberReactor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CyberFluxReactorGroup {
    private final Map<String, CyberReactor> reactors = new ConcurrentHashMap<>();

    public int size() {
        return reactors.size();
    }

    public void appendReactor(CyberReactor reactor) {
        this.reactors.put(reactor.uuid(), reactor);
    }
    public void appendReactor(Iterable<CyberReactor> reactors) {
        reactors.forEach(reactor -> {
            this.reactors.put(reactor.uuid(), reactor);
        });
    }

    public void removeReactor() {
        reactors.clear();
    }
    public void removeReactor(CyberType type) {
       findReactor(type).subscribe(reactor -> reactors.remove(reactor.uuid()));
    }
    public void removeReactor(Iterable<String> uuids) {
        Flux.fromIterable(uuids).subscribe(reactors::remove);
    }
    public void removeReactor(String uuid) {
        reactors.remove(uuid);
    }

    public Flux<CyberReactor> findReactor() {
        return Flux.fromIterable(reactors.values());
    }
    public Flux<CyberReactor> findReactor(CyberType type) {
        return findReactor().filter(reactor -> type == reactor.type());
    }
    public Flux<CyberReactor> findReactor(Iterable<String> uuids) {
        return Flux.fromIterable(uuids).flatMap(uuid -> Flux.just(reactors.get(uuid)));
    }
    public Mono<CyberReactor> findReactor(String uuid) {
        return Mono.just(reactors.get(uuid));
    }
}
