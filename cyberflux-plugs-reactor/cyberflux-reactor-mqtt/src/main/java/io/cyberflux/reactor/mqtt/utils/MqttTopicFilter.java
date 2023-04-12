package io.cyberflux.reactor.mqtt.utils;

import java.util.Set;

import io.cyberflux.reactor.mqtt.codec.MqttSubTopicStore;

public interface MqttTopicFilter {
	Set<MqttSubTopicStore> getAllTopicStore();
    Set<MqttSubTopicStore> getTopicStore(String topic);
    boolean appendTopicStore(MqttSubTopicStore store);
    boolean removeTopicStore(MqttSubTopicStore store);
    long count();
}
