package io.cyberflux.reactor.mqtt.utils;

import java.util.Set;

import io.cyberflux.reactor.mqtt.codec.MqttSubscriptionStore;

public interface MqttTopicFilter {
	Set<MqttSubscriptionStore> getAllTopicStore();
    Set<MqttSubscriptionStore> getTopicStore(String topic);
    boolean appendTopicStore(MqttSubscriptionStore store);
    boolean removeTopicStore(MqttSubscriptionStore store);
    long count();
}
