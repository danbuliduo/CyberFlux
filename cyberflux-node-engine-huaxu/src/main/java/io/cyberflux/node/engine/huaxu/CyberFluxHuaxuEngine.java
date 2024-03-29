package io.cyberflux.node.engine.huaxu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.common.utils.CyberBannerUtils;
import io.cyberflux.common.utils.CyberPackageUtils;
import io.cyberflux.meta.reactor.CyberReactor;
import io.cyberflux.node.engine.core.CyberFluxTemplateEngine;
import io.cyberflux.node.engine.core.config.CyberFluxNodeConfig;
import io.cyberflux.node.engine.core.CyberFluxNodeEngine;
import reactor.core.publisher.Flux;

public class CyberFluxHuaxuEngine extends CyberFluxTemplateEngine implements CyberFluxNodeEngine {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxHuaxuEngine.class);
    private final CyberFluxBeanFactory beanFactory;

    static {
        CyberBannerUtils.displayBanner("/io.cyberflux.banner.txt");
        log.info("<= Welcome To Use EngineCore: HUAXU - 华胥 - =>");
    }

    public static CyberFluxHuaxuEngine run() {
        return new CyberFluxHuaxuEngine();
    }

    public static CyberFluxHuaxuEngine run(Class<?> clasz) {
        return new CyberFluxHuaxuEngine(clasz);
    }

    private Flux<CyberReactor> loadReactor() {
        return beanFactory.beanFlux(CyberReactor.class);
    }

    public CyberFluxHuaxuEngine() {
		super(new CyberFluxNodeConfig());
        //super(CyberPackageUtils.getStartupClassSimpleName());
        this.beanFactory = new CyberFluxBeanFactory(CyberPackageUtils.getStartupClassName());
        loadReactor().doOnNext(reactorGroup::appendReactor).subscribe(this::startReactor);
    }

    public CyberFluxHuaxuEngine(Class<?> clasz) {
        //super(clasz.getSimpleName());
		super(new CyberFluxNodeConfig());
        this.beanFactory = new CyberFluxBeanFactory(clasz);
        loadReactor().doOnNext(reactorGroup::appendReactor).subscribe(this::startReactor);
    }
}
