package io.cyberflux.spring;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.cyberflux.node.engine.CyberFluxHuaxuEngine;
import io.cyberflux.node.engine.CyberFluxNodeEngine;


@EnableConfigurationProperties(
    CyberFluxSpringProperties.class
)
@Configuration
public class CyberFluxAutoConfiguration {
    @Bean
    CyberFluxNodeEngine cyberFluxNodeEngine(ApplicationContext context, CyberFluxSpringProperties properties) {
        return switch(properties.getEngine().getCore()) {
            case HUAXU -> CyberFluxHuaxuEngine.run();
            case SPRING -> CyberFluxSpringEngine.run(context);
            default -> CyberFluxSpringEngine.run(context);
        };
    }
}
