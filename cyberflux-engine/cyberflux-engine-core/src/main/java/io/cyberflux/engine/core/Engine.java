package io.cyberflux.engine.core;

import java.util.Collection;

import io.cyberflux.cluster.Cluster;
import io.cyberflux.meta.channel.ChannelContext;
import io.cyberflux.meta.lang.MetaType;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Engine extends Cluster {

    ChannelContext<?>[] CHANNEL_CONTEXT_OPTIONS = {
        MqttChannelContext.instance()
    };

	String version();
	/**
     * @brief 启动Reactor
     */
    void startReactor();
    void startReactor(MetaType type);
    void startReactor(Collection<String> uuids);
    void startReactor(String uuid);
    void startReactor(Reactor reactor);

    /**
     * @brief 关闭Reactor
     */
    void shutdownReactor();
    void shutdownReactor(MetaType type);
    void shutdownReactor(Collection<String> uuids);
    void shutdownReactor(String uuid);
    void shutdownReactor(Reactor reactor);

    /**
     * @brief 添加Reactor
     */
    void appendReactor(Reactor reactor);
    void appendReactor(Collection<Reactor> reactors);

    /**
     * @brief 移除Reactor
     */
    void removeReactor();
    void removeReactor(MetaType type);
    void removeReactor(Collection<String> uuids);
    void removeReactor(String uuid);

    /**
     * @brief 取出Reactor
     */
    Flux<Reactor> findReactor();
    Flux<Reactor> findReactor(MetaType type);
    Flux<Reactor> findReactor(Collection<String> uuids);
    Mono<Reactor> findReactor(String uuid);
}