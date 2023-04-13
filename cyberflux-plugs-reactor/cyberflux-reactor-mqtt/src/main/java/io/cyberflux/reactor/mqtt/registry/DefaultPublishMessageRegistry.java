package io.cyberflux.reactor.mqtt.registry;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.handler.codec.mqtt.MqttPublishMessage;

public final class DefaultPublishMessageRegistry implements MqttPublishMessageRegistry {
	private TIntObjectMap<MqttPublishMessage> messageMap;

	public DefaultPublishMessageRegistry() {
		this(0);
	}

	public DefaultPublishMessageRegistry(int initialCapacity) {
		messageMap = new TIntObjectHashMap<>(initialCapacity);
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
