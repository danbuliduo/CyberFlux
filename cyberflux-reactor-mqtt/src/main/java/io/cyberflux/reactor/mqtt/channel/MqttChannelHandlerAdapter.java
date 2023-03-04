package io.cyberflux.reactor.mqtt.channel;

import io.netty.handler.codec.mqtt.MqttMessage;
import reactor.core.publisher.Mono;

public class MqttChannelHandlerAdapter implements MqttChannelHandler {

    @Override
    public Mono<Void> onMessage(MqttChannel channel, MqttMessage message) {
        return switch(message.fixedHeader().messageType()) {
            case AUTH        -> Mono.empty();
            case CONNECT     -> Mono.empty();
            case DISCONNECT  -> Mono.empty();
            case PUBLISH     -> Mono.empty();
            case PUBACK      -> Mono.empty();
            case PUBREC      -> Mono.empty();
            case PUBREL      -> Mono.empty();
            case PUBCOMP     -> Mono.empty();
            case PINGREQ     -> Mono.empty();
            case SUBSCRIBE   -> Mono.empty();
            case UNSUBSCRIBE -> Mono.empty();
            default -> Mono.empty();
        };
    }
}
