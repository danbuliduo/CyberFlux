package io.cyberflux.reactor.mqtt.transport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebSocketTransportConfig extends MqttTransportConfig {

	@JsonProperty("path")
	protected String path = "/mqtt";

	public WebSocketTransportConfig() {
		this(8083);
	}

	public WebSocketTransportConfig(int port) {
		super(port);
	}

	public String getPath() {
		return path;
	}

	public WebSocketTransportConfig setPath(String path) {
		this.path = path;
		return this;
	}
}
