package io.cyberflux.spring;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cyberflux.node.engine.CyberFluxNodeEngine;


@EnableConfigurationProperties(CyberFluxSpringProperties.class)
@Configuration
public class CyberFluxAutoConfiguration {
    @Bean
    CyberFluxNodeEngine cyberFluxSpringEngine(ApplicationContext context, CyberFluxSpringProperties properties) {
        return CyberFluxSpringEngine.run(context);
    }
}
