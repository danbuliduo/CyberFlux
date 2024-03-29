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

public final class MqttSessionMessage {

    private String topic;
	private String channelId;
	private String properties;
    private byte[] bytes;
    private int level;
	private boolean retain;

	private MqttSessionMessage() {
		// Do Nothing
	}

    public MqttSessionMessage(
			String topic,
			String channelId,
			String properties,
			byte[] bytes,
			int level,
			boolean retain) {
		this.topic = topic;
		this.channelId = channelId;
		this.properties = properties;
		this.bytes = bytes;
		this.level = level;
		this.retain = retain;
	}

	public String getTopic() {
        return topic;
    }
	public String getChannelId() {
		return channelId;
	}
	public String getProperties() {
		return properties;
	}
    public byte[] getBytes() {
        return bytes;
    }
	public int getLevel() {
		return level;
	}
	public boolean isRetain() {
		return retain;
	}

    public MqttSessionMessage setTopic(String topic) {
        this.topic = topic;
        return this;
    }
	public MqttSessionMessage setChannelId(String channelId) {
		this.channelId = channelId;
		return this;
	}
	public MqttSessionMessage setProperties(String properties) {
		this.properties = properties;
		return this;
	}
    public MqttSessionMessage setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }
	public MqttSessionMessage setLevel(int level) {
		this.level = level;
		return this;
	}
	public MqttSessionMessage setRetain(boolean retain) {
		this.retain = retain;
		return this;
	}

	public static MqttSessionMessage fromPublishMessage(String channelId, MqttPublishMessage message) {
		Map<String, String> propertiesMap = Optional.ofNullable(
			message.variableHeader().properties().getProperties(MqttPropertyType.USER_PROPERTY.value())
		).map(properties -> {
			Map<String, String> copyMap = new HashMap<>(properties.size());
			properties.forEach(property -> {
				StringPair pair = (StringPair) property.value();
				copyMap.put(pair.key, pair.value);
			});
			return copyMap;
		}).orElseGet(HashMap::new);
		MqttSessionMessage retainMessage = new MqttSessionMessage();
		return retainMessage.setChannelId(channelId)
				.setTopic(message.variableHeader().topicName())
				.setProperties(CyberJsonUtils.toJsonString(propertiesMap))
				.setBytes(MqttByteBufUtils.copyByteBuf(message.payload()))
				.setLevel(message.fixedHeader().qosLevel().value())
				.setRetain(message.fixedHeader().isRetain());
	}

	public MqttPublishMessage toPublishMessage(MqttChannel channel) {
		final int messageId = level > 0 ? channel.generateMessageId() : 0;
		MqttQoS mqttQoS = MqttQoS.valueOf(level);
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer().writeBytes(bytes);
		MqttProperties mqttProperties = null;
		if (properties != null) {
			Map<String, String> map = CyberJsonUtils.toMap(properties, String.class, String.class);
			if (map != null) {
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
