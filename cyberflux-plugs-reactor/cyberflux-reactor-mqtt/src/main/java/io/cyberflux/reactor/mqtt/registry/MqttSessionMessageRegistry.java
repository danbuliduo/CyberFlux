package io.cyberflux.reactor.mqtt.registry;

import java.util.List;

import io.cyberflux.reactor.mqtt.codec.MqttSessionMessage;

public interface MqttSessionMessageRegistry {

    List<MqttSessionMessage> extract(String channelId);

    void append(MqttSessionMessage message);
}
