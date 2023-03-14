package io.cyberflux.example.spring.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.reactor.mqtt.CyberFluxMqttReactor;

@Configuration
public class CyberFluxReactorConfig {
    @Bean
    Reactor mqttReactor() {
        return CyberFluxMqttReactor.builder().build();
    }
}
