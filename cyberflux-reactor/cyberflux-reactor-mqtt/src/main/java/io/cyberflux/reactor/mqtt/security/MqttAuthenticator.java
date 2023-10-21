package io.cyberflux.reactor.mqtt.security;

public interface MqttAuthenticator {
	boolean auth(String username, byte[] password, String identifier);
}
