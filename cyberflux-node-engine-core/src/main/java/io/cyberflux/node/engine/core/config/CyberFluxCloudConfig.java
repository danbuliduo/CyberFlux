package io.cyberflux.node.engine.core.config;

import java.time.Duration;

public class CyberFluxCloudConfig {
	protected boolean enable;
	protected int maxRetries;
	protected String uri;
	protected Duration retryInterval;

	public CyberFluxCloudConfig() {
		this.maxRetries = 3;
		this.retryInterval = Duration.ofSeconds(10);
	}

	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getUri() {
		return uri;
	}

	public void setUri(String url) {
		this.uri = url;
	}
}
