package io.cyberflux.reactor.mqtt.protocol;

import io.cyberflux.reactor.mqtt.channel.MqttChannel;
import io.netty.handler.codec.mqtt.MqttMessage;
import reactor.core.publisher.Mono;

public class MqttMessageHandlerAdapter implements MqttMessageHandler {

    @Override
    public Mono<Void> connect(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> publish(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> disonnect(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> puback(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pubrec(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pubrel(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pubcomp(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pingreq(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> subscribe(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> unsubscribe(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }
}
