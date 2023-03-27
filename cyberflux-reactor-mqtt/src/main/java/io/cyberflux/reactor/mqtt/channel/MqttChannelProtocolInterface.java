package io.cyberflux.reactor.mqtt.channel;

import io.netty.handler.codec.mqtt.MqttMessage;
import reactor.core.publisher.Mono;

public interface MqttChannelProtocolInterface {
    Mono<Void> connect(MqttChannel channel, MqttMessage message);
    Mono<Void> publish(MqttChannel channel, MqttMessage message);
    Mono<Void> disconnect(MqttChannel channel, MqttMessage message);
    Mono<Void> puback(MqttChannel channel, MqttMessage message);
    Mono<Void> pubrec(MqttChannel channel, MqttMessage message);
    Mono<Void> pubrel(MqttChannel channel, MqttMessage message);
    Mono<Void> pubcomp(MqttChannel channel, MqttMessage message);
    Mono<Void> pingreq(MqttChannel channel, MqttMessage message);
    Mono<Void> subscribe(MqttChannel channel, MqttMessage message);
    Mono<Void> unsubscribe(MqttChannel channel, MqttMessage message);
}