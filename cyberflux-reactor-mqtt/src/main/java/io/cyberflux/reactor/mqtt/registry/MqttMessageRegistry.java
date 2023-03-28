package io.cyberflux.reactor.mqtt.registry;

import java.util.List;

import io.cyberflux.reactor.mqtt.codec.MqttRetainMessage;
import io.cyberflux.reactor.mqtt.codec.MqttSessionMessage;

public interface MqttMessageRegistry {
    List<MqttSessionMessage> findSessionMessage(String clientId);
    List<MqttRetainMessage> findRetainMessage(String topic);
    void saveSessionMessage(MqttSessionMessage sessionMessage);
    void saveRetainMessage(MqttRetainMessage retainMessage);
}
