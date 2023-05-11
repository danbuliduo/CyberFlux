package io.cyberflux.reactor.mqtt.registry;

import java.util.Set;

import io.cyberflux.reactor.mqtt.codec.MqttSubTopicStore;

public interface MqttSubTopicRegistry {

	Set<MqttSubTopicStore> findAll();

    Set<MqttSubTopicStore> findByTopic(String topic);

	void appendAll(Set<MqttSubTopicStore> stores);
	void removeAll(Set<MqttSubTopicStore> stores);

    boolean append(MqttSubTopicStore store);
    boolean remove(MqttSubTopicStore store);

    long count();
}
