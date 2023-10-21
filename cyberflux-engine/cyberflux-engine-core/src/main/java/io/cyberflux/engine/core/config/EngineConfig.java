package io.cyberflux.engine.core.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EngineConfig {
	protected String apiPath = "/engine/api";
	protected int apiPort;
	protected GatewayConfig gateway = new GatewayConfig();
	protected ClusterConfig cluster = new ClusterConfig();
	protected ReactorConfig reactor = new ReactorConfig();
}
