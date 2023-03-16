package io.cyberflux.node.engine.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.Reactor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CyberFluxReactorGroup {
    private final Map<String, Reactor> reactors = new ConcurrentHashMap<>();

    public void appendReactor(Reactor reactor) {
        this.reactors.put(reactor.uuid(), reactor);
    }

    public void appendReactor(Iterable<Reactor> reactors) {
        reactors.forEach(reactor -> {
            this.reactors.put(reactor.uuid(), reactor);
        });
    }

    public Flux<Reactor> findReactor() {
        return Flux.fromIterable(reactors.values());
    }
    public Flux<Reactor> findReactor(ReactorType type) {
        return findReactor().filter(reactor -> type == reactor.type());
    }
    public Flux<Reactor> findReactor(Iterable<String> uuids) {
        return Flux.fromIterable(uuids).flatMap(uuid -> Flux.just(reactors.get(uuid)));
    }
    public Mono<Reactor> findReactor(String uuid) {
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

    private void startReactor(Reactor reactor) {
        reactor.startAwait();
    }
    private void shutdownReactor(Reactor reactor) {
        reactor.shutdown();
    }
}
