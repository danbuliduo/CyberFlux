package io.cyberflux.reactor.mqtt.registry;

import java.util.Collection;
import java.util.Set;

import io.cyberflux.reactor.mqtt.codec.MqttSubscriptionStore;

public interface MqttSubscriptionRegistry {

	Set<MqttSubscriptionStore> findAll();

    Set<MqttSubscriptionStore> findByTopic(String topic);

	void appendAll(Collection<MqttSubscriptionStore> collection);
	void removeAll(Collection<MqttSubscriptionStore> collection);

    boolean append(MqttSubscriptionStore store);
    boolean remove(MqttSubscriptionStore store);

    long count();
}
