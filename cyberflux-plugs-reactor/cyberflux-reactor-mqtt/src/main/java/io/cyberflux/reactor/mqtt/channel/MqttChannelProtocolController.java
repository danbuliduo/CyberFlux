package io.cyberflux.reactor.mqtt.channel;

import io.netty.handler.codec.mqtt.MqttMessage;
import reactor.core.publisher.Mono;


public interface MqttChannelProtocolController {
	MqttChannelProtocolController INTTCASE = new DefaultChannelMqttProtocolController();
	Mono<Void> auth       (MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> connect    (MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> publish    (MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> disconnect (MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> puback     (MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> pubrec     (MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> pubrel     (MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> pubcomp    (MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> pingreq    (MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> subscribe  (MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> unsubscribe(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
}