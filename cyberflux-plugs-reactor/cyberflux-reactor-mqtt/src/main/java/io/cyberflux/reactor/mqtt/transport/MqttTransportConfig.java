package io.cyberflux.reactor.mqtt.transport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.cyberflux.meta.medium.MediumType;
import io.cyberflux.meta.reactor.AbstractTransportConfig;

@JsonIgnoreProperties({"type"})
public abstract class MqttTransportConfig extends AbstractTransportConfig {
	public MqttTransportConfig(int port, MediumType type) {
		super(port, type);
	}
}
