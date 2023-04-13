package io.cyberflux.reactor.mqtt.utils;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttPubAckMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttSubAckMessage;
import io.netty.handler.codec.mqtt.MqttSubAckPayload;
import io.netty.handler.codec.mqtt.MqttUnsubAckMessage;

public final class MqttMessageBuilder {


    private static MqttFixedHeader fixedHeader(MqttMessageType type, int remaining) {
        return new MqttFixedHeader(type, false, MqttQoS.AT_MOST_ONCE, false, remaining);
    }

	private static MqttFixedHeader fixedHeader(
			MqttMessageType type, MqttQoS mqttQoS, int remaining) {
		return new MqttFixedHeader(type, false, mqttQoS, false, remaining);
	}

	public static MqttPublishMessage wrappedPublishMessage(
			MqttPublishMessage message, MqttQoS mqttQoS, int messageId) {
		return new MqttPublishMessage(
			fixedHeader(MqttMessageType.PUBLISH, mqttQoS, 0x00),
		 	new MqttPublishVariableHeader(
				message.variableHeader().topicName(),
				messageId,
				message.variableHeader().properties()
			),
			message.payload().copy()
		);
	}

	public static MqttConnAckMessage buildConnAckMessage(
			MqttConnectReturnCode returnCode, MqttProperties properties) {
		return new MqttConnAckMessage(
			fixedHeader(MqttMessageType.CONNACK, 0x02),
			new MqttConnAckVariableHeader(returnCode, false, properties)
		);
	}

	public static MqttPublishMessage buildPublishMessage() {
		return null;
	}

	public static MqttPubAckMessage buildPubAckMessage(int messageId) {
		return new MqttPubAckMessage(
			fixedHeader(MqttMessageType.PUBACK, 0x02),
			MqttMessageIdVariableHeader.from(messageId)
		);
	}

	public static MqttPubAckMessage buildPubRecMessage(int messageId) {
		return new MqttPubAckMessage(
			fixedHeader(MqttMessageType.PUBREC, 0x02),
			MqttMessageIdVariableHeader.from(messageId)
		);
	}

	public static MqttPubAckMessage buildPubRelMessage(int messageId) {
		return new MqttPubAckMessage(
			fixedHeader(MqttMessageType.PUBREL, 0x02),
			MqttMessageIdVariableHeader.from(messageId)
		);
	}

	public static MqttPubAckMessage buildPubCompMessage(int messageId) {
		return new MqttPubAckMessage(
			fixedHeader(MqttMessageType.PUBCOMP, 0x02),
			MqttMessageIdVariableHeader.from(messageId)
		);
	}

	public static MqttConnAckMessage buildConnAckMessage(MqttConnectReturnCode returnCode) {
		return MqttMessageBuilder.buildConnAckMessage(returnCode, MqttProperties.NO_PROPERTIES);
	}

    public static MqttSubAckMessage buildSubAckMessage(
			int messageId, Iterable<Integer> codes) {
        return new MqttSubAckMessage(
			fixedHeader(MqttMessageType.SUBACK, 0x02),
            MqttMessageIdVariableHeader.from(messageId),
            new MqttSubAckPayload(codes)
		);
    }

    public static MqttUnsubAckMessage buildUnsubAckMessage(int messageId) {
        return new MqttUnsubAckMessage(
			fixedHeader(MqttMessageType.UNSUBACK, 0x02),
            MqttMessageIdVariableHeader.from(messageId)
		);
    }

    public static MqttMessage buildPingRespMessage() {
        return new MqttMessage(fixedHeader(MqttMessageType.PINGRESP, 0x00));
    }
}
