package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.reactor.mqtt.MqttReactorOption;
import io.cyberflux.reactor.mqtt.exception.MqttTransportTypeException;
import reactor.core.Disposable;

public final class MqttTransportFactory {

	public static MqttTransport<? extends Disposable, ? extends MqttTransportConfig>
	 		defaultTransport() {
		return new TcpTransport();
	}

	public static MqttTransport<? extends Disposable, ? extends MqttTransportConfig>
			createTransport(MqttTransportConfig config) {
		if(config instanceof TcpTransportConfig i) {
			return new TcpTransport(i);
		} else if(config instanceof WebSocketTransportConfig i) {
			return new WebSocketTransport(i);
		}
		throw new MqttTransportTypeException("This type is not supported.");
	}

	public static MqttTransport<? extends Disposable, ? extends MqttTransportConfig>
			createTransport(MqttReactorOption option) {
		return switch (option.getType()) {
			case "tcp" -> {
				TcpTransportConfig config = new TcpTransportConfig(option.getPort());
				yield new TcpTransport(config);
			}
			case "ws" -> {
				WebSocketTransportConfig config = new WebSocketTransportConfig(option.getPort());
				config.setPath(option.getPath());
				yield new WebSocketTransport(config);
			}
			default -> throw new MqttTransportTypeException("This type is not supported.");
		};
	}
}
