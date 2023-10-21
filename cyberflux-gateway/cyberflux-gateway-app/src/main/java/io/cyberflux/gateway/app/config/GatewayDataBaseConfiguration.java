package io.cyberflux.gateway.app.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;

import io.r2dbc.spi.ConnectionFactory;

@SpringBootConfiguration
public class GatewayDataBaseConfiguration {
	@Bean
  	ConnectionFactoryInitializer h2Initializer(ConnectionFactory connectionFactory) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    	ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		populator.addScripts(new ClassPathResource("scripts/h2.schema.sql"));
		populator.addScripts(new ClassPathResource("scripts/h2.data.sql"));
    	initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(populator);
    	return initializer;
  	}
}
