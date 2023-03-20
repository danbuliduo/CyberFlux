package io.cyberflux.node.engine.huaxu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.reactor.CyberFluxReactor;
import io.cyberflux.node.engine.core.CyberFluxTemplateEngine;
import io.cyberflux.node.engine.core.CyberFluxNodeEngine;
import io.cyberflux.node.engine.huaxu.utils.CyberBannerUtils;
import io.cyberflux.node.engine.huaxu.utils.CyberPackageUtils;
import reactor.core.publisher.Flux;

public class CyberFluxHuaxuEngine extends CyberFluxTemplateEngine implements CyberFluxNodeEngine {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxHuaxuEngine.class);
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

    private Flux<CyberFluxReactor> loadReactor() {
        return beanFactory.beanFlux(CyberFluxReactor.class);
    }

    public CyberFluxHuaxuEngine() {
        super(CyberPackageUtils.getStartupClassSimpleName());
        this.beanFactory = new CyberFluxBeanFactory(CyberPackageUtils.getStartupClassName());
        loadReactor().doOnNext(reactorGroup::appendReactor).subscribe(this::startReactor);
    }

    public CyberFluxHuaxuEngine(Class<?> clasz) {
        super(clasz.getSimpleName());
        this.beanFactory = new CyberFluxBeanFactory(clasz);
        loadReactor().doOnNext(reactorGroup::appendReactor).subscribe(this::startReactor);
    }
}
