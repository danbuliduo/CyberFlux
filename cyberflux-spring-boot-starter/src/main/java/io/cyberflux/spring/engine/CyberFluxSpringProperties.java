package io.cyberflux.spring.engine;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.cyberflux.node.engine.core.config.CyberFluxNodeConfig;

@ConfigurationProperties(prefix = "cyberflux.node-engine")
public class CyberFluxSpringProperties extends CyberFluxNodeConfig {

}
