package io.cyberflux.reactor.mqtt.codec;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.cyberflux.common.utils.CyberJsonUtils;
import io.cyberflux.reactor.mqtt.channel.MqttChannel;
import io.cyberflux.reactor.mqtt.utils.MqttByteBufUtils;
import io.cyberflux.reactor.mqtt.utils.MqttMessageBuilder;
import io.netty.handler.codec.mqtt.MqttProperties.MqttPropertyType;
import io.netty.handler.codec.mqtt.MqttProperties.StringPair;
import io.netty.handler.codec.mqtt.MqttProperties.UserProperties;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;


public final class MqttRetainMessage {
    private String topic;
	private String properties;
    private byte[] bytes;
    private int level;

	private MqttRetainMessage() {
		// Do Nothing
	}

    public MqttRetainMessage(
			String topic,
			String properties,
			byte[] bytes,
			int level) {
		this.topic = topic;
		this.properties = properties;
		this.bytes = bytes;
		this.level = level;
	}

	public String getTopic() {
        return topic;
    }
	public String getProperties() {
		return properties;
	}
    public int getLevel() {
        return level;
    }
    public byte[] getBytes() {
        return bytes;
    }

    public MqttRetainMessage setTopic(String topic) {
        this.topic = topic;
        return this;
    }

	public MqttRetainMessage setProperties(String properties) {
		this.properties = properties;
		return this;
	}

    public MqttRetainMessage setLevel(int level) {
        this.level = level;
        return this;
    }
    public MqttRetainMessage setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

	public static MqttRetainMessage fromPublishMessage(MqttPublishMessage message) {
		Map<String, String> propertiesMap = Optional.ofNullable(
			message.variableHeader().properties()
					.getProperties(MqttPropertyType.USER_PROPERTY.value())
		).map(properties -> {
			Map<String, String> copyMap = new HashMap<>(properties.size());
			properties.forEach(property -> {
				StringPair pair = (StringPair) property.value();
            	copyMap.put(pair.key, pair.value);
			});
			return copyMap;
		}).orElseGet(HashMap::new);
		MqttRetainMessage retainMessage = new MqttRetainMessage();
		return retainMessage.setTopic(message.variableHeader().topicName())
				.setProperties(CyberJsonUtils.toJsonString(propertiesMap))
				.setBytes(MqttByteBufUtils.copyByteBuf(message.payload()))
				.setLevel(message.fixedHeader().qosLevel().value());
	}

	public MqttPublishMessage toPublishMessage(MqttChannel channel) {
		final int messageId = level > 0 ? channel.generateMessageId() : 0;
		MqttQoS mqttQoS = MqttQoS.valueOf(level);
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer().writeBytes(bytes);
		MqttProperties mqttProperties = null;
		if(properties != null) {
			Map<String, String> map = CyberJsonUtils.toMap(properties, String.class, String.class);
			if(map != null) {
				mqttProperties = new MqttProperties();
				UserProperties userProperties = new UserProperties();
				map.forEach(userProperties::add);
				mqttProperties.add(userProperties);
			}
		}
		return MqttMessageBuilder.buildPublishMessage(
			false, mqttQoS, messageId, topic, byteBuf, mqttProperties
		);
	}
}
