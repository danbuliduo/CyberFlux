package io.cyberflux.reactor.mqtt.transport;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cyberflux.meta.medium.MediumType;

public class WebSocketTransportConfig extends MqttTransportConfig {

	@JsonProperty("path")
	protected String path = "/mqtt";

	public WebSocketTransportConfig() {
		super(8883, MediumType.WEBSOCKET);
	}

	public String getPath() {
		return path;
	}

	public WebSocketTransportConfig setPath(String path) {
		this.path = path;
		return this;
	}

}
