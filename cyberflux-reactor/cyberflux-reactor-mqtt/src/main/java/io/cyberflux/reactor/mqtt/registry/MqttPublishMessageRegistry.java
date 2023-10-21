package io.cyberflux.reactor.mqtt.registry;

import io.netty.handler.codec.mqtt.MqttPublishMessage;

public interface MqttPublishMessageRegistry {
	MqttPublishMessage findById(int messageId);
	MqttPublishMessage removeById(int messageId);
	boolean save(int messageId, MqttPublishMessage message);
    boolean contains(int messageId);
	void clear();
}
