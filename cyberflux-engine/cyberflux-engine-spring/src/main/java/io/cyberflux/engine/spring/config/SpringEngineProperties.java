package io.cyberflux.engine.spring.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import io.cyberflux.engine.core.config.EngineConfig;
import io.cyberflux.engine.actuator.ActuatorConfig;
import io.cyberflux.engine.spring.utils.PathUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "cyberflux.engine")
public class SpringEngineProperties extends EngineConfig {

    @NestedConfigurationProperty
    private ActuatorConfig actuator = new ActuatorConfig();

    @Override
    public void setApiPath (String path) {
        this.apiPath = PathUtils.normalizePath(path);
    }
}
