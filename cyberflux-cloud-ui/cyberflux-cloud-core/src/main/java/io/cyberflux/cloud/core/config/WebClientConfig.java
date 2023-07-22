package io.cyberflux.cloud.core.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootConfiguration
public class WebClientConfig {

	@Bean
	WebClient.Builder balancedWebClientBuilder() {
		return WebClient.builder();
	}
}
