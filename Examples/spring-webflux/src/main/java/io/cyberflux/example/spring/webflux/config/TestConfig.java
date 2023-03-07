package io.cyberflux.example.spring.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cyberflux.meta.reactor.ReactiveServer;
import io.cyberflux.reactor.mqtt.MqttReactor;

@Configuration
public class TestConfig {
    @Bean
    ReactiveServer mqttServer() {
        return MqttReactor.builder().build();
    }
}
