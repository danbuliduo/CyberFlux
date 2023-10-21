package io.cyberflux.reactor.mqtt.security;

public final class MqttAuthenticatorFactory {
	private final MqttAuthenticatorConfig config;

	public MqttAuthenticatorFactory() {
		this(null);
	}

	public MqttAuthenticatorFactory(MqttAuthenticatorConfig config) {
		this.config = config;
	}

	public MqttAuthenticator createAuthenticator() {
		if(config == null) {
			return new MqttNoneAuthenticator();
		}
		return new MqttNoneAuthenticator();
	}

	public interface AuthenticatorProvider {
		MqttAuthenticatorFactory provider(MqttAuthenticatorConfig config);
	}

	public static AuthenticatorProvider instance() {
		return MqttAuthenticatorFactory::new;
	}
}
