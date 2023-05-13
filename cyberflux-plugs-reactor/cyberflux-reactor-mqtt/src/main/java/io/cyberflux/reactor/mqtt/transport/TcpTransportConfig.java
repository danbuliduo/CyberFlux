package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.meta.data.CyberType;

public class TcpTransportConfig extends MqttTransportConfig {

	public TcpTransportConfig() {
		this(1883);
	}

	public TcpTransportConfig(int port) {
		super(CyberType.TCP, port);
	}
}
