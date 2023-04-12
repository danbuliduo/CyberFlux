package io.cyberflux.reactor.mqtt.exception;

public final class MqttMessageTypeException extends IllegalArgumentException {
	public MqttMessageTypeException(String message) {
		super(message);
	}
}
