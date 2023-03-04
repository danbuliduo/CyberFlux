package io.cyberflux.reactor.mqtt.channel;

import io.netty.handler.codec.mqtt.MqttMessage;
import reactor.core.publisher.Mono;

public interface MqttChannelHandler {
    Mono<Void> onMessage(MqttChannel channel, MqttMessage message);
}





