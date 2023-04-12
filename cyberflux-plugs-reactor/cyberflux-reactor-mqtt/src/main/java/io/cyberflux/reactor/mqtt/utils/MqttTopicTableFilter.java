package io.cyberflux.reactor.mqtt.utils;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import io.cyberflux.reactor.mqtt.codec.MqttSubTopicStore;

public final class MqttTopicTableFilter implements MqttTopicFilter {
    private final LoadingCache<String, Set<MqttSubTopicStore>> topicStores;
    private final LongAdder topicNumber;

    public MqttTopicTableFilter() {
        topicStores = Caffeine.newBuilder().build(key -> {
            return new CopyOnWriteArraySet<>();
        });
        topicNumber = new LongAdder();
    }

    @Override
    public Set<MqttSubTopicStore> getTopicStore(String topic) {
        return topicStores.get(topic);
    }

    @Override
    public Set<MqttSubTopicStore> getAllTopicStore() {
        return topicStores.asMap().values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public boolean appendTopicStore(MqttSubTopicStore store) {
        if(topicStores.get(store.topic()).add(store)) {
            topicNumber.add(+1);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeTopicStore(MqttSubTopicStore store) {
        if(topicStores.get(store.topic()).remove(store)) {
            topicNumber.add(-1);
            return true;
        }
        return false;
    }

    @Override
    public long count() {
       return topicNumber.sum();
    }
}
