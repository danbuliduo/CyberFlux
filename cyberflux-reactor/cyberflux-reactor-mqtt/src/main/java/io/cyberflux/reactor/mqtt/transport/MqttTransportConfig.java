package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.meta.transport.TransportConfig;

public abstract class MqttTransportConfig extends TransportConfig {

	public MqttTransportConfig(int port) {
		super(port);
	}
}
