package io.cyberflux.examples.node3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cyberflux.meta.reactor.CyberReactor;
import io.cyberflux.reactor.mqtt.MqttReactor;
import io.cyberflux.reactor.mqtt.transport.TcpTransport;
import io.cyberflux.reactor.mqtt.transport.TcpTransportConfig;

@Configuration
public class NodeReactorConfig {
	@Bean
	CyberReactor mqttReactor() {
		TcpTransport transport = new TcpTransport(new TcpTransportConfig(1885));
		return MqttReactor.builder().transport(transport).build();
	}
}
