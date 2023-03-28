package io.cyberflux.node.engine.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.reactor.CyberFluxReactor;
import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.cluster.CyberFluxAbstractClusterNode;
import io.cyberflux.node.engine.core.container.CyberFluxReactorGroup;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CyberFluxTemplateEngine extends CyberFluxAbstractClusterNode implements CyberFluxMetaEngine {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxTemplateEngine.class);
    protected final CyberFluxReactorGroup reactorGroup = new CyberFluxReactorGroup();

    public CyberFluxTemplateEngine(String nodeName) {
        super(nodeName);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> this.shutdownReactor()));
    }

    @Override
    public Flux<CyberFluxReactor> findReactor() {
        return reactorGroup.findReactor();
    }

    @Override
    public Flux<CyberFluxReactor> findReactor(ReactorType type) {
        return reactorGroup.findReactor(type);
    }

    @Override
    public Flux<CyberFluxReactor> findReactor(Iterable<String> uuids) {
        return reactorGroup.findReactor(uuids);
    }

    @Override
    public Mono<CyberFluxReactor> findReactor(String uuid) {
        return reactorGroup.findReactor(uuid);
    }

    @Override
    public void appendReactor(CyberFluxReactor reactor) {
        reactorGroup.appendReactor(reactor);
    }

    @Override
    public void appendReactor(Iterable<CyberFluxReactor> reactors) {
        reactorGroup.appendReactor(reactors);
    }

    @Override
    public void removeReactor() {
        reactorGroup.removeReactor();
    }

    @Override
    public void removeReactor(ReactorType type) {
        reactorGroup.removeReactor(type);
    }

    @Override
    public void removeReactor(Iterable<String> uuids) {
        reactorGroup.removeReactor(uuids);
    }

    @Override
    public void removeReactor(String uuid) {
        reactorGroup.removeReactor(uuid);
    }

    @Override
    public void startReactor() {
        log.info("NodeEngine::StartReactor => [All - count:{}]", reactorGroup.size());
        findReactor().subscribe(this::startReactor);
    }

    @Override
    public void startReactor(ReactorType type) {
        findReactor(type).subscribe(this::startReactor);
    }

    @Override
    public void startReactor(Iterable<String> uuids) {
        findReactor(uuids).subscribe(this::startReactor);
    }

    @Override
    public void startReactor(String uuid) {
        findReactor(uuid).subscribe(this::startReactor);
    }

    @Override
    public void startReactor(CyberFluxReactor reactor) {
        reactor.startAwait();
    }

    @Override
    public void shutdownReactor() {
        findReactor().subscribe(this::shutdownReactor);
        log.info("NodeEngine::ShutdownReactor => [All - count:{}]", reactorGroup.size());
    }

    @Override
    public void shutdownReactor(ReactorType type) {
        findReactor(type).subscribe(this::shutdownReactor);
    }

    @Override
    public void shutdownReactor(Iterable<String> uuids) {
        findReactor(uuids).subscribe(this::shutdownReactor);
    }

    @Override
    public void shutdownReactor(String uuid) {
        findReactor(uuid).subscribe(this::shutdownReactor);
    }

    @Override
    public void shutdownReactor(CyberFluxReactor reactor) {
        reactor.shutdown();
    }
}
