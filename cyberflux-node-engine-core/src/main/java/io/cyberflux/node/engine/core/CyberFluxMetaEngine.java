package io.cyberflux.node.engine.core;

import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.CyberFluxReactor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CyberFluxMetaEngine {
    /**
     * @brief 启动Reactor
     */
    void startReactor();
    void startReactor(ReactorType type);
    void startReactor(Iterable<String> uuids);
    void startReactor(String uuid);
    void startReactor(CyberFluxReactor reactor);

    /**
     * @brief 关闭Reactor
     */
    void shutdownReactor();
    void shutdownReactor(ReactorType type);
    void shutdownReactor(Iterable<String> uuids);
    void shutdownReactor(String uuid);
    void shutdownReactor(CyberFluxReactor reactor);

    /**
     * @brief 添加Reactor
     */
    void appendReactor(CyberFluxReactor reactor);
    void appendReactor(Iterable<CyberFluxReactor> reactors);

    /**
     * @brief 移除Reactor
     */
    void removeReactor();
    void removeReactor(ReactorType type);
    void removeReactor(Iterable<String> uuids);
    void removeReactor(String uuid);

    /**
     * @brief 取出Reactor
     */
    Flux<CyberFluxReactor> findReactor();
    Flux<CyberFluxReactor> findReactor(ReactorType type);
    Flux<CyberFluxReactor> findReactor(Iterable<String> uuids);
    Mono<CyberFluxReactor> findReactor(String uuid);
}
