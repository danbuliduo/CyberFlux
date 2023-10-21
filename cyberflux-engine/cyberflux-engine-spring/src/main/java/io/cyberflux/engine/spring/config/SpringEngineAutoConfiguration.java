package io.cyberflux.engine.spring.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import io.cyberflux.engine.spring.SpringEngine;
import io.cyberflux.engine.spring.service.EngineActuatorService;

@Lazy(false)
@SpringBootConfiguration(proxyBeanMethods = false)
@ImportAutoConfiguration({SpringEngineWebConfiguration.class})
@EnableConfigurationProperties({SpringEngineProperties.class})
public class SpringEngineAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    EngineActuatorService actuatorService(SpringEngineProperties properties) {
        return new EngineActuatorService(properties.getActuator());
    }
    @Bean
    @ConditionalOnMissingBean
    SpringEngine springEngine(ApplicationContext context, SpringEngineProperties properties) {
        return SpringEngine.run(context, properties);
    }
}
