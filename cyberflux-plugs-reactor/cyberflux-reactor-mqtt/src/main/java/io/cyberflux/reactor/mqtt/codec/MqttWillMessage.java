package io.cyberflux.reactor.mqtt.codec;

import java.util.Arrays;

import io.cyberflux.reactor.mqtt.utils.MqttMessageBuilder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;

public final class MqttWillMessage {

	private String topic;
	private byte[] bytes;
	private int level;
	private boolean retain;

	private MqttWillMessage() {
		// Do Nothing
	}

	public MqttWillMessage(
			String topic,
			byte[] bytes,
			int level,
			boolean retain) {
		this.topic = topic;
		this.bytes = bytes;
		this.level = level;
		this.retain = retain;
	}

	public String getTopic() {
		return topic;
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

	public MqttWillMessage setTopic(String topic) {
		this.topic = topic;
		return this;
	}
	public MqttWillMessage setBytes(byte[] bytes) {
		this.bytes = bytes;
		return this;
	}
	public MqttWillMessage setLevel(int level) {
		this.level = level;
		return this;
	}
	public MqttWillMessage setRetain(boolean retain) {
		this.retain = retain;
		return this;
	}

	public static MqttWillMessage fromConnectMessage(MqttConnectMessage message) {
		MqttWillMessage willMessage = new MqttWillMessage();
		return willMessage.setTopic(message.payload().willTopic())
				.setBytes(message.payload().willMessageInBytes())
				.setLevel(message.variableHeader().willQos())
				.setRetain(message.variableHeader().isWillRetain());
	}

	public MqttPublishMessage toPublishMessage(int level, int messageId) {
		return MqttMessageBuilder.buildPublishMessage(
			false, MqttQoS.valueOf(level), messageId, topic, Unpooled.wrappedBuffer(bytes)
		);
	}

	@Override
	public String toString() {
		return "MqttWillMessage [topic=" + topic + ", bytes=" + Arrays.toString(bytes)
				+ ", level=" + level + ", retain=" + retain + "]";
	}
}
