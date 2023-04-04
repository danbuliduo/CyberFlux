package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.reactor.mqtt.exception.TransportInstanceofException;
import reactor.core.Disposable;


public final class MqttTransportFactory {

	public static MqttTransport<? extends Disposable, ? extends MqttTransportConfig>
			defaultTransport() {
		return new TcpTransport(new TcpTransportConfig());
	}

	public static MqttTransport<? extends Disposable, ? extends MqttTransportConfig>
			createTransport(MqttTransportConfig config) {
		if(config instanceof TcpTransportConfig) {
			return new TcpTransport((TcpTransportConfig) config);
		} else if(config instanceof WebSocketTransportConfig) {
			return new WebSocketTransport((WebSocketTransportConfig) config);
		}
		throw new TransportInstanceofException("This type is not supported.");
	}
}
