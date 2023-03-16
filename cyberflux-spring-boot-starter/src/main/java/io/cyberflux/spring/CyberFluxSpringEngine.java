package io.cyberflux.spring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.meta.reactor.cluster.AbstractClusterNode;
import io.cyberflux.node.engine.CyberFluxNodeEngine;
import io.cyberflux.node.engine.container.CyberFluxReactorGroup;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CyberFluxSpringEngine extends AbstractClusterNode implements CyberFluxNodeEngine {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxSpringEngine.class);
    private final CyberFluxReactorGroup reactorGroup;

    static {
        log.info("<= Welcome To Use EngineCore: SPRING - 昭节 - =>");
    }

    public static CyberFluxSpringEngine run(ApplicationContext context) {
        return new CyberFluxSpringEngine(context);
    }

    public CyberFluxSpringEngine(ApplicationContext context) {
        super(context.getApplicationName());
        System.out.println(context.getApplicationName());
        this.reactorGroup = new CyberFluxReactorGroup();
        Flux.fromArray(context.getBeanNamesForType(Reactor.class))
                .flatMap(name -> Flux.just(context.getBean(name)))
                .cast(Reactor.class)
                .doOnNext(reactorGroup::appendReactor)
                .subscribe(this::startReactor);
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


    public void appendReactor(Reactor reactor) {
        reactorGroup.appendReactor(reactor);
    }

    public void startReactor(Reactor reactor) {
        reactor.startAwait();
        log.info("Reactor ==> {} - type:{}, uuid:{}", reactor.getClass(), reactor.type(), reactor.uuid());
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
    public void appendReactor(Iterable<Reactor> reactors) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'appendReactor'");
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
