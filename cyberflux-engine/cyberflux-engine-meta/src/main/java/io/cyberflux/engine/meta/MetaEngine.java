package io.cyberflux.engine.meta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.utils.io.BannerUtils;
import io.cyberflux.utils.jar.PackageUtils;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.engine.core.TemplateEngine;
import io.cyberflux.engine.core.config.EngineConfig;
import reactor.core.publisher.Flux;

public class MetaEngine extends TemplateEngine {
    private static final Logger log = LoggerFactory.getLogger(MetaEngine.class);
    private final MetaEngineBeanFactory beanFactory;

    static {
        BannerUtils.display("/io.cyberflux.banner.txt");
        log.info("<= Welcome To Use EngineCore: META - 元 - =>");
    }

    public static MetaEngine run() {
        return new MetaEngine();
    }

    public static MetaEngine run(Class<?> clasz) {
        return new MetaEngine(clasz);
    }

    private Flux<Reactor> loadReactor() {
        return beanFactory.beanFlux(Reactor.class);
    }

    public MetaEngine() {
		super(new EngineConfig());
        this.beanFactory = new MetaEngineBeanFactory(PackageUtils.getStartupClassName());
        loadReactor().doOnNext(this::appendReactor).subscribe(this::startReactor);
    }

    public MetaEngine(Class<?> clasz) {
		super(new EngineConfig());
        this.beanFactory = new MetaEngineBeanFactory(clasz);
        loadReactor().doOnNext(this::appendReactor).subscribe(this::startReactor);
    }
}
