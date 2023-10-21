package io.cyberflux.reactor.mqtt.registry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import io.cyberflux.reactor.mqtt.codec.MqttSessionMessage;

public final class DefaultSessionMessageRegistry implements MqttSessionMessageRegistry {

    private Map<String, List<MqttSessionMessage>> sessionMap;

    public DefaultSessionMessageRegistry() {
        sessionMap = new ConcurrentHashMap<>();
    }

	@Override
	public List<MqttSessionMessage> extract(String channelId) {
		return sessionMap.remove(channelId);
	}

	@Override
	public void append(MqttSessionMessage message) {
		sessionMap.computeIfAbsent(message.getClientId(), key ->
		    new CopyOnWriteArrayList<>()
		).add(message);
	}

}
