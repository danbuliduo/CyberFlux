package io.cyberflux.example.spring.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cyberflux.meta.reactor.CyberReactor;
import io.cyberflux.reactor.mqtt.MqttReactor;

@Configuration
public class CyberFluxReactorConfig {
    @Bean
    CyberReactor mqttReactor() {
        return MqttReactor.builder().build();
    }
}
