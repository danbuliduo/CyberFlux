package io.cyberflux.node.engine.core.config;

public class CyberFluxCloudConfig {
	protected boolean enable;
	protected String url;

	public CyberFluxCloudConfig() {

	}

	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
