package io.cyberflux.gateway.app.context.config;

import java.time.Duration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonWithTokenConfig {
    private String secretKey = "CYBER_FLUX_GATEWAY";
	private String prefix = "";
	private Duration expires = Duration.ofDays(1L);
}
