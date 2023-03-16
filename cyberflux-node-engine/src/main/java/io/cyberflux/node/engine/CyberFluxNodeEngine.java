package io.cyberflux.node.engine;

import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.meta.reactor.cluster.ClusterNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CyberFluxNodeEngine extends ClusterNode {
    /**
     * @brief 启动Reactor
     */
    void startReactor();
    void startReactor(ReactorType type);
    void startReactor(Iterable<String> uuids);
    void startReactor(String uuid);
    void startReactor(Reactor reactor);

    /**
     * @brief 关闭Reactor
     */
    void shutdownReactor();
    void shutdownReactor(ReactorType type);
    void shutdownReactor(Iterable<String> uuids);
    void shutdownReactor(String uuid);
    void shutdownReactor(Reactor reactor);

    /**
     * @brief 添加Reactor
     */
    void appendReactor(Reactor reactor);
    void appendReactor(Iterable<Reactor> reactors);

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
    Flux<Reactor> findReactor();
    Flux<Reactor> findReactor(ReactorType type);
    Flux<Reactor> findReactor(Iterable<String> uuids);
    Mono<Reactor> findReactor(String uuid);
}
