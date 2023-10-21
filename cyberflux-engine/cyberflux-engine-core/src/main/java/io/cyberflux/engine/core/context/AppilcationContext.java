package io.cyberflux.engine.core.context;

import io.cyberflux.engine.core.config.EngineConfig;

public final class AppilcationContext {

	private final EngineConfig config;

	public AppilcationContext(EngineConfig config) {
		this.config = config;
	}

	public EngineConfig config() {
		return this.config;
	}
}
