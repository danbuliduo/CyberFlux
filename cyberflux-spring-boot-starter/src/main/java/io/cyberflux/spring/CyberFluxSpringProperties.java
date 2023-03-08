package io.cyberflux.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.cyberflux.node.engine.config.CyberFluxClusterConfig;
import io.cyberflux.node.engine.config.CyberFluxReactorConfig;

@ConfigurationProperties(prefix = "cyberflux")
public class CyberFluxSpringProperties {

    private CyberFluxClusterConfig cluster;
    private CyberFluxReactorConfig reactor;

    public CyberFluxClusterConfig getCluster() {
        return cluster;
    }
    public CyberFluxReactorConfig getReactor() {
        return reactor;
    }


    public void setCluster(CyberFluxClusterConfig cluster) {
        this.cluster = cluster;
    }
    public void setReactor(CyberFluxReactorConfig reactor) {
        this.reactor = reactor;
    }
}
