package io.cyberflux.reactor.mqtt.transport;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cyberflux.meta.data.CyberType;

public class WebSocketTransportConfig extends MqttTransportConfig {

	@JsonProperty("path")
	protected String path = "/mqtt";

	public WebSocketTransportConfig() {
		super(CyberType.WEBSOCKET, 8883);
	}

	public String getPath() {
		return path;
	}

	public WebSocketTransportConfig setPath(String path) {
		this.path = path;
		return this;
	}
}
