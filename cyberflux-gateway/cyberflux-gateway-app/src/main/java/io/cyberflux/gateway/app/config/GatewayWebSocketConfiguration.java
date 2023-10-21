package io.cyberflux.gateway.app.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import io.cyberflux.gateway.app.context.GatewayWebSocketMapping;

@SpringBootConfiguration
public class GatewayWebSocketConfiguration {
    @Bean
    HandlerMapping handlerMapping() {
        return new GatewayWebSocketMapping();
    }
    @Bean
    WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
