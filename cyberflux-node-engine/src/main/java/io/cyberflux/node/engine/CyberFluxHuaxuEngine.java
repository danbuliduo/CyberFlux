package io.cyberflux.node.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.reactor.ProtocolType;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.node.engine.container.CyberFluxReactorGroup;
import io.cyberflux.node.engine.core.CyberFluxBeanFactory;
import io.cyberflux.node.engine.utils.CyberBannerUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class CyberFluxHuaxuEngine implements CyberFluxNodeEngine {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxHuaxuEngine.class);
    private static final CyberFluxReactorGroup reactorGroup = CyberFluxReactorGroup.INSTANCE;
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

    public CyberFluxHuaxuEngine() {
        beanFactory= new CyberFluxBeanFactory();
        loadReactorFlux().doOnNext(reactorGroup::saveReactor).subscribe(this::startReactor);
    }

    public CyberFluxHuaxuEngine(Class<?> clasz) {
        beanFactory = new CyberFluxBeanFactory(clasz);
        loadReactorFlux().doOnNext(reactorGroup::saveReactor).subscribe(this::startReactor);
    }

    public Mono<Reactor> loadReactorMono(String injectName) {
        return Mono.just(beanFactory.findBean(injectName)).cast(Reactor.class);
    }

    public Flux<Reactor> loadReactorFlux() {
        return beanFactory.beanFlux(Reactor.class);
    }

    public Flux<Reactor> loadReactorFlux(ProtocolType type) {
        return loadReactorFlux().filter(reactor -> type == reactor.protocolType());
    }

    public void saveReactor(Reactor reactor) {
        reactorGroup.saveReactor(reactor);
    }

    public void startReactor(Reactor reactor) {
        reactor.startAwait();
        log.info("Reactor ==> {} - type:{}, uuid:{}", reactor.getClass(), reactor.protocolType(), reactor.uuid());
    }
}
