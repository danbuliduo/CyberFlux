package io.cyberflux.node.engine.core.config;

public class CyberFluxNodeConfig {

	protected CyberFluxCloudConfig cloud;
	protected CyberFluxClusterConfig cluster;
	protected CyberFluxReactorConfig reactor;

	public CyberFluxCloudConfig getCloud() {
		return cloud;
	}
	public CyberFluxClusterConfig getCluster() {
		return cluster;
	}
	public CyberFluxReactorConfig getReactor() {
		return reactor;
	}

	public void setCloud(CyberFluxCloudConfig cloud) {
		this.cloud = cloud;
	}
	public void setCluster(CyberFluxClusterConfig clusterConfig) {
		this.cluster = clusterConfig;
	}
	public void setReactor(CyberFluxReactorConfig reactorConfig) {
		this.reactor = reactorConfig;
	}
}
