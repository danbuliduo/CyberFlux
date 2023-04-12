package io.cyberflux.reactor.mqtt.exception;

public final class MqttTransportTypeException extends IllegalArgumentException {
	public MqttTransportTypeException(String message) {
		super(message);
	}
}
