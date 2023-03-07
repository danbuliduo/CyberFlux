package io.cyberflux.node.engine.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.cyberflux.meta.reactor.ProtocolType;
import io.cyberflux.meta.reactor.ReactiveServer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CyberFluxReactorGroup {
    private final Map<String, ReactiveServer> reactors = new ConcurrentHashMap<>();
    public static CyberFluxReactorGroup INSTANCE = new CyberFluxReactorGroup();

    public void saveReactor(ReactiveServer reactor) {
        reactors.put(reactor.uuid(), reactor);
    }

    public Flux<ReactiveServer> findReactor() {
        return Flux.fromIterable(reactors.values());
    }
    public Flux<ReactiveServer> findReactor(ProtocolType type) {
        return findReactor().filter(reactor -> type == reactor.protocolType());
    }
    public Flux<ReactiveServer> findReactor(Iterable<String> uuids) {
        return Flux.fromIterable(uuids).flatMap(uuid -> Flux.just(reactors.get(uuid)));
    }
    public Mono<ReactiveServer> findReactor(String uuid) {
        return Mono.just(reactors.get(uuid));
    }

    public void startReactor() {
        findReactor().subscribe(this::startReactor);
    }
    public void startReactor(ProtocolType type) {
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
    public void shutdownReactor(ProtocolType type) {
        findReactor(type).subscribe(this::shutdownReactor);
    }
    public void shutdownReactor(Iterable<String> uuids) {
        findReactor(uuids).subscribe(this::shutdownReactor);
    }
    public void shutdownReactor(String uuid) {
        findReactor(uuid).subscribe(this::shutdownReactor);
    }

    private void startReactor(ReactiveServer reactor) {
        reactor.startAwait();
    }
    private void shutdownReactor(ReactiveServer reactor) {
        reactor.shutdown();
    }
}
