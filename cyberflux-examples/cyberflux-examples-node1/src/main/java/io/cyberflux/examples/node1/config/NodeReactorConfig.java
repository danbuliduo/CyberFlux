package io.cyberflux.examples.node1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cyberflux.meta.reactor.CyberReactor;
import io.cyberflux.reactor.mqtt.MqttReactor;

@Configuration
public class NodeReactorConfig {
	@Bean
	CyberReactor mqttReactor() {
		return MqttReactor.builder().build();
	}
}
