package io.cyberflux.reactor.mqtt.registry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import io.cyberflux.reactor.mqtt.codec.MqttRetainMessage;
import io.cyberflux.reactor.mqtt.utils.MqttTopicRegexUtils;

public final class DefaultRetainMessageRegistry implements MqttRetainMessageRegistry {

	private Map<String, MqttRetainMessage> messageMap;

	public DefaultRetainMessageRegistry() {
		messageMap = new ConcurrentHashMap<>();
	}

	@Override
	public List<MqttRetainMessage> findByTopic(String topic) {
		return messageMap.keySet().stream()
				.filter(key -> key.matches(MqttTopicRegexUtils.regexTopic(topic)))
				.map(messageMap::get)
				.collect(Collectors.toList());
	}

	@Override
	public void save(MqttRetainMessage message) {
		messageMap.put(message.getTopic(), message);
	}

}
