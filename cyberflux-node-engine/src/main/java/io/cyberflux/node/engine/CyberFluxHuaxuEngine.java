package io.cyberflux.node.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.meta.reactor.cluster.AbstractClusterNode;
import io.cyberflux.node.engine.container.CyberFluxReactorGroup;
import io.cyberflux.node.engine.core.CyberFluxBeanFactory;
import io.cyberflux.node.engine.utils.CyberBannerUtils;
import io.cyberflux.node.engine.utils.CyberPackageUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CyberFluxHuaxuEngine extends AbstractClusterNode implements CyberFluxNodeEngine {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxHuaxuEngine.class);
    private final CyberFluxReactorGroup reactorGroup;
    private final CyberFluxBeanFactory beanFactory;

    static {
        CyberBannerUtils.banner();
        log.info("<= Welcome To Use EngineCore: HUAXU - 华胥 - =>");
    }

    public static CyberFluxHuaxuEngine run() {
        return new CyberFluxHuaxuEngine();
    }

    public static CyberFluxHuaxuEngine run(Class<?> clasz) {
        return new CyberFluxHuaxuEngine(clasz);
    }

    private Flux<Reactor> loadReactor() {
        return beanFactory.beanFlux(Reactor.class);
    }

    public CyberFluxHuaxuEngine() {
        super(CyberPackageUtils.getStartupClassSimpleName());
        this.reactorGroup = new CyberFluxReactorGroup();
        this.beanFactory = new CyberFluxBeanFactory(CyberPackageUtils.getStartupClassName());
        loadReactor().doOnNext(reactorGroup::appendReactor).subscribe(this::startReactor);
    }

    public CyberFluxHuaxuEngine(Class<?> clasz) {
        super(clasz.getSimpleName());
        this.reactorGroup = new CyberFluxReactorGroup();
        this.beanFactory = new CyberFluxBeanFactory(clasz);
        loadReactor().doOnNext(reactorGroup::appendReactor).subscribe(this::startReactor);
    }

    @Override
    public void startReactor(Reactor reactor) {
        reactor.startAwait();
        log.info("Reactor ==> {} - type:{}, uuid:{}", reactor.getClass(), reactor.type(), reactor.uuid());
    }

    @Override
    public Flux<Reactor> findReactor() {
        return reactorGroup.findReactor();
    }

    @Override
    public Flux<Reactor> findReactor(ReactorType type) {
        return reactorGroup.findReactor(type);
    }

    @Override
    public Flux<Reactor> findReactor(Iterable<String> uuids) {
        return reactorGroup.findReactor(uuids);
    }

    @Override
    public Mono<Reactor> findReactor(String uuid) {
        return reactorGroup.findReactor(uuid);
    }

    @Override
    public void appendReactor(Reactor reactor) {
        reactorGroup.appendReactor(reactor);
    }

    @Override
    public void appendReactor(Iterable<Reactor> reactors) {
        reactorGroup.appendReactor(reactors);
    }

    @Override
    public void startReactor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startReactor'");
    }

    @Override
    public void startReactor(ReactorType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startReactor'");
    }

    @Override
    public void startReactor(Iterable<String> uuids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startReactor'");
    }

    @Override
    public void startReactor(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startReactor'");
    }

    @Override
    public void shutdownReactor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shutdownReactor'");
    }

    @Override
    public void shutdownReactor(ReactorType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shutdownReactor'");
    }

    @Override
    public void shutdownReactor(Iterable<String> uuids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shutdownReactor'");
    }

    @Override
    public void shutdownReactor(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shutdownReactor'");
    }

    @Override
    public void shutdownReactor(Reactor reactor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shutdownReactor'");
    }

    @Override
    public void removeReactor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeReactor'");
    }

    @Override
    public void removeReactor(ReactorType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeReactor'");
    }

    @Override
    public void removeReactor(Iterable<String> uuids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeReactor'");
    }

    @Override
    public void removeReactor(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeReactor'");
    }


}
