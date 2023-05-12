package io.cyberflux.reactor.mqtt.cluster;

import io.cyberflux.meta.data.CyberClusterMessage;
import io.cyberflux.meta.reactor.ScaleCubeClusterAdapter;
import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;


public class MqttClusterHandler extends ScaleCubeClusterAdapter {
	private final MqttChannelContext context;

	public MqttClusterHandler(MqttChannelContext context) {
		this.context = context;
	}

	@Override
	public void messageConsumer(CyberClusterMessage message) {
		System.out.println("OK");
	}

}
