package io.cyberflux.reactor.mqtt.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import io.netty.handler.codec.mqtt.MqttPublishMessage;

public final class DefaultPublishMessageRegistry implements MqttPublishMessageRegistry {

	private final Map<Integer ,MqttPublishMessage> messageMap;

	public DefaultPublishMessageRegistry() {
		this(0);
	}

	public DefaultPublishMessageRegistry(int initialCapacity) {
		messageMap = new ConcurrentHashMap<>(initialCapacity);
	}

	@Override
	public MqttPublishMessage findById(int messageId) {
		return messageMap.get(messageId);
	}

	@Override
	public MqttPublishMessage removeById(int messageId) {
		return messageMap.remove(messageId);
	}

	@Override
	public boolean save(int messageId, MqttPublishMessage message) {
		if(!contains(messageId)) {
			messageMap.put(messageId, message);
			return true;
		}
		return false;
	}


	@Override
	public boolean contains(int messageId) {
		return messageMap.containsKey(messageId);
	}

	@Override
	public void clear() {
		messageMap.clear();
	}
}
