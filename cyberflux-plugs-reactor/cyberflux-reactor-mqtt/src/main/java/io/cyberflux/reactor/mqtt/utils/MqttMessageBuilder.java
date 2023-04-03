package io.cyberflux.reactor.mqtt.utils;

import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
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

    public static MqttConnAckMessage buildConnAck(MqttConnectReturnCode returnCode) {
        return new MqttConnAckMessage(fixedHeader(MqttMessageType.CONNACK, 0x02),
            new MqttConnAckVariableHeader(returnCode, false)
        );
    }

    public static MqttSubAckMessage buildSubAck(int messageId, Iterable<Integer> reasonCodes) {
        return new MqttSubAckMessage(fixedHeader(MqttMessageType.SUBACK, 0x02),
            MqttMessageIdVariableHeader.from(messageId),
            new MqttSubAckPayload(reasonCodes)
        );
    }

    public static MqttUnsubAckMessage buildUnsubAck(int messageId) {
        return new MqttUnsubAckMessage(fixedHeader(MqttMessageType.UNSUBACK, 0x02),
            MqttMessageIdVariableHeader.from(messageId)
        );
    }

    public static MqttMessage buildPingResp() {
        return new MqttMessage(fixedHeader(MqttMessageType.PINGRESP, 0x00));
    }
}
