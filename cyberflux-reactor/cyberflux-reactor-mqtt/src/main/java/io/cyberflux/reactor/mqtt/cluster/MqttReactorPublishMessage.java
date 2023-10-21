package io.cyberflux.reactor.mqtt.cluster;

import io.cyberflux.meta.cluster.ClusterAction;
import io.cyberflux.meta.cluster.ClusterMessage;
import io.cyberflux.reactor.mqtt.utils.MqttByteBufUtils;
import io.cyberflux.reactor.mqtt.utils.MqttMessageBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;

public class MqttReactorPublishMessage extends ClusterMessage {
	private int level;
	private boolean retain;
	private String topic;
	private String channelId;
	private MqttProperties properties;

	public MqttReactorPublishMessage() {
		super(ClusterAction.PUSH);
	}

	public MqttReactorPublishMessage(
			int level,
			boolean retain,
			String channelId,
			String topic,
			Object payload,
			MqttProperties properties) {
		super(payload, ClusterAction.PUSH);
		this.level = level;
		this.retain = retain;
		this.topic = topic;
		this.channelId = channelId;
		this.properties = properties;
	}

	public static MqttReactorPublishMessage fromPublishMessage(
			MqttPublishMessage message, String channelId) {
		MqttPublishVariableHeader variableHeader = message.variableHeader();
		MqttFixedHeader fixedHeader = message.fixedHeader();
		return new MqttReactorPublishMessage(
			fixedHeader.qosLevel().value(),
			fixedHeader.isRetain(),
			channelId,
			variableHeader.topicName(),
			MqttByteBufUtils.copyByteBuf(message.payload()),
			variableHeader.properties()
		);
	}

	public MqttPublishMessage toPublishMessage() {
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.buffer().writeBytes((byte[]) payload);
		return MqttMessageBuilder.buildPublishMessage(
			false, MqttQoS.valueOf(level), 0, topic, byteBuf, properties
		);
	}

	public int getLevel() {
		return level;
	}

	public boolean isRetain() {
		return retain;
	}

	public String getTopic() {
		return topic;
	}

	public String getClientId() {
		return channelId;
	}


	public MqttProperties getProperties() {
		return properties;
	}

	public MqttReactorPublishMessage setLevel(int level) {
		this.level = level;
		return this;
	}

	public MqttReactorPublishMessage setRetain(boolean retain) {
		this.retain = retain;
		return this;
	}

	public MqttReactorPublishMessage setTopic(String topic) {
		this.topic = topic;
		return this;
	}

	public MqttReactorPublishMessage setChannelId(String channelId) {
		this.channelId = channelId;
		return this;
	}

	public MqttReactorPublishMessage setProperties(MqttProperties properties) {
		this.properties = properties;
		return this;
	}

}
