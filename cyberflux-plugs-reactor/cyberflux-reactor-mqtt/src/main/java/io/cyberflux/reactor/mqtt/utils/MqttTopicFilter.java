package io.cyberflux.reactor.mqtt.utils;

import java.util.Set;

import io.cyberflux.reactor.mqtt.codec.MqttTopicStore;

public interface MqttTopicFilter {
    Set<MqttTopicStore> getTopicStore(String topic);
    Set<MqttTopicStore> getAllTopicStore();
    boolean appendTopicStore(MqttTopicStore store);
    boolean removeTopicStore(MqttTopicStore store);
    long count();
}
