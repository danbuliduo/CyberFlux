package io.cyberflux.reactor.mqtt.channel;

import io.cyberflux.reactor.mqtt.registry.MqttMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttTopicRegistry;

public class MqttChannelHandlerContext {
	private MqttChannelGroup channelGroup;
	private MqttTopicRegistry topicRegistry;
	private MqttMessageRegistry messageRegistry;
	private MqttChannelInboundHandler channelHandler;

	public MqttChannelHandlerContext() {

	}
}
