package io.cyberflux.gateway.app.context;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootConfiguration
@EnableConfigurationProperties({
	GatewaySecurityProperties.class
})
public class GatewayAutoConfiguration {

}
