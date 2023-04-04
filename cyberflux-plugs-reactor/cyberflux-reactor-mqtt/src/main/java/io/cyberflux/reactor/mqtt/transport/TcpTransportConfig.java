package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.meta.medium.MediumType;

public class TcpTransportConfig extends MqttTransportConfig {
	public TcpTransportConfig() {
		super(1883, MediumType.TCP);
	}
}
