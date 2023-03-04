package io.cyberflux.reactor.mqtt.channel;

import io.netty.handler.codec.mqtt.MqttMessage;

public interface MqttChannelHandler {
    void onRegister(MqttChannel channel);
    void onInactive(MqttChannel channel);
    void onMessage(MqttChannel channel, MqttMessage message);
}





