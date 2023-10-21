package io.cyberflux.reactor.mqtt.registry;

import java.util.List;

import io.cyberflux.reactor.mqtt.codec.MqttRetainMessage;

public interface MqttRetainMessageRegistry {
	List<MqttRetainMessage> findByTopic(String topic);
	void save(MqttRetainMessage message);
}
