package io.cyberflux.reactor.mqtt.transport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.TransportConfig;

@JsonIgnoreProperties({"type"})
public abstract class MqttTransportConfig extends TransportConfig {

	public MqttTransportConfig(CyberType type, int port) {
		super(type, port);
	}
}
