package io.cyberflux.reactor.mqtt.registry;

import java.util.Set;

import io.cyberflux.reactor.mqtt.codec.MqttTopicStore;

public interface MqttTopicRegistry {
    Set<MqttTopicStore> getTopicStore(String topic);

    Set<MqttTopicStore> getAllTopicStore();

    boolean appendTopicStore(MqttTopicStore store);

    boolean removeTopicStore(MqttTopicStore store);

    long count();
}
