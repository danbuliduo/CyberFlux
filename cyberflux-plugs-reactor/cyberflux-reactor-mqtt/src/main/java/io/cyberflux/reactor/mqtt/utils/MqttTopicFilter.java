package io.cyberflux.reactor.mqtt.utils;

import java.util.Set;

import io.cyberflux.reactor.mqtt.codec.MqttTopicStore;

public interface MqttTopicFilter {
	Set<MqttTopicStore> getAllTopicStore();
    Set<MqttTopicStore> getTopicStore(String topic);
    boolean appendTopicStore(MqttTopicStore store);
    boolean removeTopicStore(MqttTopicStore store);
    long count();
}
