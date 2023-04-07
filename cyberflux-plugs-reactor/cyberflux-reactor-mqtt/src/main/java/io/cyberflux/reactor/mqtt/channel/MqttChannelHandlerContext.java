package io.cyberflux.reactor.mqtt.channel;

import io.cyberflux.reactor.mqtt.registry.MqttMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttTopicRegistry;

public class MqttChannelHandlerContext {

	protected MqttChannelGroup channelGroup;
	protected MqttTopicRegistry topicRegistry;
	protected MqttMessageRegistry messageRegistry;
	protected MqttChannelInboundHandler inboundHandler;

	public MqttChannelHandlerContext() {
		inboundHandler = new DefaultMqttChannelInboundHandler(null);
	}

	public void applyChannel(MqttChannel channel) {
		inboundHandler.channelActivate(this, channel);
	}

	public MqttChannelGroup getChannelGroup() {
		return channelGroup;
	}
	public MqttTopicRegistry getTopicRegistry() {
		return topicRegistry;
	}
	public MqttChannelInboundHandler  getChannelInboundHandler() {
		return inboundHandler;
	}
}
