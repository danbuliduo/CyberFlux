package io.cyberflux.reactor.mqtt.cluster;

import io.cyberflux.meta.data.cluster.CyberClusterAction;
import io.cyberflux.meta.data.cluster.CyberClusterMessage;
import io.cyberflux.reactor.mqtt.utils.MqttByteBufUtils;
import io.cyberflux.reactor.mqtt.utils.MqttMessageBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;

public class MqttClusterPublishMessage extends CyberClusterMessage {
	private int level;
	private boolean retain;
	private String topic;
	private String channelId;
	private MqttProperties properties;

	public MqttClusterPublishMessage() {
		super(CyberClusterAction.PUSH);
	}

	public MqttClusterPublishMessage(
			int level,
			boolean retain,
			String channelId,
			String topic,
			Object payload,
			MqttProperties properties) {
		super(payload, CyberClusterAction.PUSH);
		this.level = level;
		this.retain = retain;
		this.topic = topic;
		this.channelId = channelId;
		this.properties = properties;
	}

	public static MqttClusterPublishMessage fromPublishMessage(
			MqttPublishMessage message, String channelId) {
		MqttPublishVariableHeader variableHeader = message.variableHeader();
		MqttFixedHeader fixedHeader = message.fixedHeader();
		return new MqttClusterPublishMessage(
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

	public String getChannelId() {
		return channelId;
	}


	public MqttProperties getProperties() {
		return properties;
	}

	public MqttClusterPublishMessage setLevel(int level) {
		this.level = level;
		return this;
	}

	public MqttClusterPublishMessage setRetain(boolean retain) {
		this.retain = retain;
		return this;
	}

	public MqttClusterPublishMessage setTopic(String topic) {
		this.topic = topic;
		return this;
	}

	public MqttClusterPublishMessage setChannelId(String channelId) {
		this.channelId = channelId;
		return this;
	}

	public MqttClusterPublishMessage setProperties(MqttProperties properties) {
		this.properties = properties;
		return this;
	}

}
