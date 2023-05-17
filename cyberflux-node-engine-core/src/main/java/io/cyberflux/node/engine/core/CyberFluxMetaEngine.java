package io.cyberflux.node.engine.core;

import java.util.Collection;

import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.CyberReactor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CyberFluxMetaEngine {
    /**
     * @brief 启动Reactor
     */
    void startReactor();
    void startReactor(CyberType type);
    void startReactor(Collection<String> uuids);
    void startReactor(String uuid);
    void startReactor(CyberReactor reactor);

    /**
     * @brief 关闭Reactor
     */
    void shutdownReactor();
    void shutdownReactor(CyberType type);
    void shutdownReactor(Collection<String> uuids);
    void shutdownReactor(String uuid);
    void shutdownReactor(CyberReactor reactor);

    /**
     * @brief 添加Reactor
     */
    void appendReactor(CyberReactor reactor);
    void appendReactor(Collection<CyberReactor> reactors);

    /**
     * @brief 移除Reactor
     */
    void removeReactor();
    void removeReactor(CyberType type);
    void removeReactor(Collection<String> uuids);
    void removeReactor(String uuid);

    /**
     * @brief 取出Reactor
     */
    Flux<CyberReactor> findReactor();
    Flux<CyberReactor> findReactor(CyberType type);
    Flux<CyberReactor> findReactor(Collection<String> uuids);
    Mono<CyberReactor> findReactor(String uuid);
}
