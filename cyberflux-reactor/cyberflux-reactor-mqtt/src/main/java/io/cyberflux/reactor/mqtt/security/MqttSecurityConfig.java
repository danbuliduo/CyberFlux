package io.cyberflux.reactor.mqtt.security;

public final class MqttSecurityConfig {
	private MqttAuthenticatorConfig authenticatorConfig;

	public MqttAuthenticatorConfig getAuthenticatorConfig() {
		return authenticatorConfig;
	}

	public void setAuthenticatorConfig(MqttAuthenticatorConfig authenticatorConfig) {
		this.authenticatorConfig = authenticatorConfig;
	}
}
