package io.cyberflux.reactor.mqtt.cluster;

import io.cyberflux.meta.data.CyberClusterMessage;
import io.cyberflux.meta.reactor.TemplateClusterAdapter;
import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;
import io.netty.handler.codec.mqtt.MqttPublishMessage;


public class MqttClusterHandler extends TemplateClusterAdapter {
	private final MqttChannelContext context;

	public MqttClusterHandler(MqttChannelContext context) {
		this.context = context;
	}

	@Override
	public void messageConsumer(CyberClusterMessage message) {
		MqttPublishMessage pubMessage;
		context.getTopicRegistry().findByTopic(null);
	}

}
