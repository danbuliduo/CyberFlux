package io.cyberflux.reactor.mqtt.utils;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import io.cyberflux.reactor.mqtt.codec.MqttSubscriptionStore;

public final class MqttTopicTableFilter implements MqttTopicFilter {
    private final LoadingCache<String, Set<MqttSubscriptionStore>> topicStores;
    private final LongAdder topicNumber;

    public MqttTopicTableFilter() {
        topicStores = Caffeine.newBuilder().build(key -> {
            return new CopyOnWriteArraySet<>();
        });
        topicNumber = new LongAdder();
    }

    @Override
    public Set<MqttSubscriptionStore> getTopicStore(String topic) {
        return topicStores.get(topic);
    }

    @Override
    public Set<MqttSubscriptionStore> getAllTopicStore() {
        return topicStores.asMap().values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public boolean appendTopicStore(MqttSubscriptionStore store) {
        if(topicStores.get(store.getTopic()).add(store)) {
			store.linkChannel();
            topicNumber.add(+1);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeTopicStore(MqttSubscriptionStore store) {
        if(topicStores.get(store.getTopic()).remove(store)) {
			store.unlinkChannel();
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
