package io.cyberflux.reactor.mqtt.cluster;

import io.cyberflux.meta.data.cluster.CyberClusterAction;
import io.cyberflux.meta.data.cluster.CyberClusterMessage;

public class MqttClusterCloseMessage extends CyberClusterMessage {
	private String channelId;

	public MqttClusterCloseMessage() {
		super(CyberClusterAction.CLOSE);
	}

	public MqttClusterCloseMessage(String channelId) {
		super(CyberClusterAction.CLOSE);
		this.channelId = channelId;
	}

	public String getChannelId() {
		return channelId;
	}

	public MqttClusterCloseMessage setChannelId(String channelId) {
		this.channelId = channelId;
		return this;
	}
}
