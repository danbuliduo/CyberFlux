package io.cyberflux.spring;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(CyberFluxSpringProperties.class)
@Configuration
public class CyberFluxAutoConfiguration {
    @Bean
    SpringBootNodeEngine springbootNodeEngine(CyberFluxSpringProperties properties) {
        return new SpringBootNodeEngine();
    }
}
