package io.cyberflux.node.engine.core.config;

public class CyberFluxNodeConfig {

	protected CyberFluxClusterConfig cluster;
	protected CyberFluxReactorConfig reactor;

	public CyberFluxClusterConfig getCluster() {
		return cluster;
	}
	public CyberFluxReactorConfig getReactor() {
		return reactor;
	}
	public void setCluster(CyberFluxClusterConfig clusterConfig) {
		this.cluster = clusterConfig;
	}
	public void setReactor(CyberFluxReactorConfig reactorConfig) {
		this.reactor = reactorConfig;
	}
}
