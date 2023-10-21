package io.cyberflux.reactor.mqtt.security;

public final class MqttNoneAuthenticator implements MqttAuthenticator {
	@Override
	public boolean auth(String username, byte[] password, String identifier) {
		return true;
	}
}
