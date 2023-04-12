package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.reactor.mqtt.exception.MqttTransportTypeException;
import reactor.core.Disposable;

public final class MqttTransportFactory {

	public static MqttTransport<? extends Disposable, ? extends MqttTransportConfig>
	 		defaultTransport() {
		return new TcpTransport();
	}

	public static MqttTransport<? extends Disposable, ? extends MqttTransportConfig>
			createTransport(MqttTransportConfig config) {
		if(config instanceof TcpTransportConfig) {
			return new TcpTransport((TcpTransportConfig) config);
		} else if(config instanceof WebSocketTransportConfig) {
			return new WebSocketTransport((WebSocketTransportConfig) config);
		}
		throw new MqttTransportTypeException("This type is not supported.");
	}
}
