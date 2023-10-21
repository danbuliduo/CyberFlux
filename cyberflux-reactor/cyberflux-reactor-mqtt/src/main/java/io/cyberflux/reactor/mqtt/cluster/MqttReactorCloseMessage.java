package io.cyberflux.reactor.mqtt.cluster;

import io.cyberflux.meta.cluster.ClusterAction;
import io.cyberflux.meta.cluster.ClusterMessage;


public class MqttReactorCloseMessage extends ClusterMessage {

	private String clientId;

	public MqttReactorCloseMessage(String clientId) {
		super(ClusterAction.CLOSE);
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
