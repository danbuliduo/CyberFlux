package io.cyberflux.node.engine.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.cluster.CyberFluxAbstractClusterNode;
import io.cyberflux.meta.data.CyberMessage;
import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.CyberReactor;
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
    public Flux<CyberReactor> findReactor() {
        return reactorGroup.findReactor();
    }

    @Override
    public Flux<CyberReactor> findReactor(CyberType type) {
        return reactorGroup.findReactor(type);
    }

    @Override
    public Flux<CyberReactor> findReactor(Iterable<String> uuids) {
        return reactorGroup.findReactor(uuids);
    }

    @Override
    public Mono<CyberReactor> findReactor(String uuid) {
        return reactorGroup.findReactor(uuid);
    }

    @Override
    public void appendReactor(CyberReactor reactor) {
        reactorGroup.appendReactor(reactor);
    }

    @Override
    public void appendReactor(Iterable<CyberReactor> reactors) {
        reactorGroup.appendReactor(reactors);
    }

    @Override
    public void removeReactor() {
        reactorGroup.removeReactor();
    }

    @Override
    public void removeReactor(CyberType type) {
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
        log.info("NodeEngine::StartAll => [count:{}]", reactorGroup.size());
        findReactor().subscribe(this::startReactor);
    }

    @Override
    public void startReactor(CyberType type) {
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
    public void startReactor(CyberReactor reactor) {
        reactor.startAwait();
    }

    @Override
    public void shutdownReactor() {
        findReactor().subscribe(this::shutdownReactor);
        log.info("NodeEngine::ShutdownAll => [count:{}]", reactorGroup.size());
    }

    @Override
    public void shutdownReactor(CyberType type) {
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
    public void shutdownReactor(CyberReactor reactor) {
        reactor.shutdown();
    }

	@Override
	public <M> void spreadMessage(CyberMessage message) {
		throw new UnsupportedOperationException("Unimplemented method 'spreadMessage'");
	}

	@Override
	public <M> void spreadMessage(CyberMessage message, String modeName) {
		throw new UnsupportedOperationException("Unimplemented method 'spreadMessage'");
	}
}
