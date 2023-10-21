package io.cyberflux.reactor.mqtt.transport;

public class TcpTransportConfig extends MqttTransportConfig {

	public TcpTransportConfig() {
		this(1883);
	}

	public TcpTransportConfig(int port) {
		super(port);
	}
}
