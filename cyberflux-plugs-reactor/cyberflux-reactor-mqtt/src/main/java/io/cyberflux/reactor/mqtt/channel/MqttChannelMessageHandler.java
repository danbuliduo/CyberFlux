package io.cyberflux.reactor.mqtt.channel;

import io.netty.handler.codec.mqtt.MqttMessage;
import reactor.core.publisher.Mono;

public interface MqttChannelMessageHandler {
	void channelRead(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
    Mono<Void> onMessageProcess(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message);
}
