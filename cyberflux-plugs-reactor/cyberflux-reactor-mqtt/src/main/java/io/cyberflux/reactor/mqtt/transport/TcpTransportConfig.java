package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.meta.data.CyberType;

public class TcpTransportConfig extends MqttTransportConfig {

	public TcpTransportConfig() {
		super(CyberType.TCP, 1883);
	}
}
