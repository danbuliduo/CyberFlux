package io.cyberflux.reactor.mqtt.registry;

import java.util.Set;

import io.cyberflux.reactor.mqtt.codec.MqttTopicStore;

public interface MqttTopicRegistry {
	Set<MqttTopicStore> findAll();
    Set<MqttTopicStore> findByTopic(String topic);

	void appendAll(Set<MqttTopicStore> stores);
    boolean append(MqttTopicStore store);
    boolean remove(MqttTopicStore store);

    long count();
}
