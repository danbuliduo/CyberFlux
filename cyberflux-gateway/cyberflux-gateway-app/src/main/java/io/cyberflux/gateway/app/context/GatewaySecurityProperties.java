package io.cyberflux.gateway.app.context;

import io.cyberflux.gateway.app.context.config.JsonWithTokenConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "cyberflux.gateway.security")
public class GatewaySecurityProperties {

	@NestedConfigurationProperty
	private JsonWithTokenConfig jwt;

}
