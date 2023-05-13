package io.cyberflux.reactor.mqtt.transport;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.cyberflux.meta.data.CyberType;

public class WebSocketTransportConfig extends MqttTransportConfig {

	@JsonProperty("path")
	protected String path = "/mqtt";

	public WebSocketTransportConfig() {
		this(8083);
	}

	public WebSocketTransportConfig(int port) {
		super(CyberType.WEBSOCKET, port);
	}

	public String getPath() {
		return path;
	}

	public WebSocketTransportConfig setPath(String path) {
		this.path = path;
		return this;
	}
}
