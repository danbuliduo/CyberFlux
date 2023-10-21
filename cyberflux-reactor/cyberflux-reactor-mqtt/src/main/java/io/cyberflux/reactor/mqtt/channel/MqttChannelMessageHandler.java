package io.cyberflux.reactor.mqtt.channel;

import io.netty.handler.codec.mqtt.MqttMessage;

public interface MqttChannelMessageHandler {
	void channelRead(MqttChannelContext context, MqttChannel channel, MqttMessage message);
}
