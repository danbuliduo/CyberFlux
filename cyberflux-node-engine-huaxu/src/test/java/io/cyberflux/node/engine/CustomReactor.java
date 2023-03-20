package io.cyberflux.node.engine;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.node.engine.huaxu.annotation.CyberMetaObject;
import io.cyberflux.meta.reactor.CyberFluxReactor;
import io.cyberflux.meta.reactor.ReactorStatus;
import reactor.core.publisher.Mono;


@CyberMetaObject
public class CustomReactor implements CyberFluxReactor {
    private static final Logger log = LoggerFactory.getLogger(CustomReactor.class);

    private String uuid = UUID.randomUUID().toString();

    @Override
    public ReactorType type() {
        return ReactorType.CUSTOM;
    }

    @Override
    public Mono<CyberFluxReactor> start() {
        log.info("自定义Reactor:做点什么");
        return Mono.empty();
    }

    @Override
    public CyberFluxReactor startAwait() {
        return start().block();
    }

    @Override
    public void shutdown() {
    }

    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public ReactorStatus status() {
       return ReactorStatus.OPERATION;
    }
}
