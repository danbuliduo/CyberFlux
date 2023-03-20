package io.cyberflux.node.engine.core.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.CyberFluxReactor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CyberFluxReactorGroup {
    private final Map<String, CyberFluxReactor> reactors = new ConcurrentHashMap<>();

    public int size() {
        return reactors.size();
    }

    public void appendReactor(CyberFluxReactor reactor) {
        this.reactors.put(reactor.uuid(), reactor);
    }
    public void appendReactor(Iterable<CyberFluxReactor> reactors) {
        reactors.forEach(reactor -> {
            this.reactors.put(reactor.uuid(), reactor);
        });
    }

    public void removeReactor() {
        reactors.clear();
    }
    public void removeReactor(ReactorType type) {
       findReactor(type).subscribe(reactor -> reactors.remove(reactor.uuid()));
    }
    public void removeReactor(Iterable<String> uuids) {
        Flux.fromIterable(uuids).subscribe(reactors::remove);
    }
    public void removeReactor(String uuid) {
        reactors.remove(uuid);
    }

    public Flux<CyberFluxReactor> findReactor() {
        return Flux.fromIterable(reactors.values());
    }
    public Flux<CyberFluxReactor> findReactor(ReactorType type) {
        return findReactor().filter(reactor -> type == reactor.type());
    }
    public Flux<CyberFluxReactor> findReactor(Iterable<String> uuids) {
        return Flux.fromIterable(uuids).flatMap(uuid -> Flux.just(reactors.get(uuid)));
    }
    public Mono<CyberFluxReactor> findReactor(String uuid) {
        return Mono.just(reactors.get(uuid));
    }

    public void startReactor() {
        findReactor().subscribe(this::startReactor);
    }
    public void startReactor(ReactorType type) {
        findReactor(type).subscribe(this::startReactor);
    }
    public void startReactor(Iterable<String> uuids) {
        findReactor(uuids).subscribe(this::startReactor);
    }
    public void startReactor(String uuid) {
        findReactor(uuid).subscribe(this::startReactor);
    }

    public void shutdownReactor() {
        findReactor().subscribe(this::shutdownReactor);
    }
    public void shutdownReactor(ReactorType type) {
        findReactor(type).subscribe(this::shutdownReactor);
    }
    public void shutdownReactor(Iterable<String> uuids) {
        findReactor(uuids).subscribe(this::shutdownReactor);
    }
    public void shutdownReactor(String uuid) {
        findReactor(uuid).subscribe(this::shutdownReactor);
    }

    private void startReactor(CyberFluxReactor reactor) {
        reactor.startAwait();
    }
    private void shutdownReactor(CyberFluxReactor reactor) {
        reactor.shutdown();
    }
}
