package io.cyberflux.reactor.mqtt.cache;

import java.util.Set;

import io.cyberflux.reactor.mqtt.codec.MqttTopicStore;

public interface MqttTopicCache {
    Set<MqttTopicStore> getTopicStore(String topic);

    Set<MqttTopicStore> getAllTopicStore();

    boolean appendTopicStore(MqttTopicStore store);

    boolean removeTopicStore(MqttTopicStore store);

    long count();
}
