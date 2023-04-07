package io.cyberflux.reactor.mqtt.utils;

import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPubAckMessage;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttSubAckMessage;
import io.netty.handler.codec.mqtt.MqttSubAckPayload;
import io.netty.handler.codec.mqtt.MqttUnsubAckMessage;

public final class MqttMessageBuilder {
    private static MqttFixedHeader fixedHeader(MqttMessageType type, int remaining) {
        return new MqttFixedHeader(
            type, false, MqttQoS.AT_MOST_ONCE, false, remaining
        );
    }

	public static MqttPubAckMessage buildPubAckMessage(int messageId) {
		return new MqttPubAckMessage(fixedHeader(MqttMessageType.PUBACK, 0x02),
			MqttMessageIdVariableHeader.from(messageId)
		);
	}

	public static MqttPubAckMessage buildPubRecMessage(int messageId) {
		return new MqttPubAckMessage(fixedHeader(MqttMessageType.PUBREC, 0x02),
			MqttMessageIdVariableHeader.from(messageId)
		);
	}

	public static MqttPubAckMessage buildPubRelMessage(int messageId) {
		return new MqttPubAckMessage(fixedHeader(MqttMessageType.PUBREL, 0x02),
			MqttMessageIdVariableHeader.from(messageId)
		);
	}

	public static MqttPubAckMessage buildPubCompMessage(int messageId) {
		return new MqttPubAckMessage(fixedHeader(MqttMessageType.PUBCOMP, 0x02),
				MqttMessageIdVariableHeader.from(messageId));
	}

    public static MqttConnAckMessage buildConnAckMessage(MqttConnectReturnCode returnCode) {
        return new MqttConnAckMessage(fixedHeader(MqttMessageType.CONNACK, 0x02),
            new MqttConnAckVariableHeader(returnCode, false)
        );
    }

    public static MqttSubAckMessage buildSubAckMessage(int messageId, Iterable<Integer> reasonCodes) {
        return new MqttSubAckMessage(fixedHeader(MqttMessageType.SUBACK, 0x02),
            MqttMessageIdVariableHeader.from(messageId),
            new MqttSubAckPayload(reasonCodes)
		);
    }

    public static MqttUnsubAckMessage buildUnsubAckMessage(int messageId) {
        return new MqttUnsubAckMessage(fixedHeader(MqttMessageType.UNSUBACK, 0x02),
            MqttMessageIdVariableHeader.from(messageId)
        );
    }

    public static MqttMessage buildPingRespMessage() {
        return new MqttMessage(fixedHeader(MqttMessageType.PINGRESP, 0x00));
    }
}
