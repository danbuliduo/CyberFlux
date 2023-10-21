package io.cyberflux.engine.core.config;

import java.time.Duration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GatewayConfig {
	protected boolean enable = false;
	protected int maxRetries = 3;
	protected String uri = "";
	protected Duration retryInterval = Duration.ofSeconds(60);
}
