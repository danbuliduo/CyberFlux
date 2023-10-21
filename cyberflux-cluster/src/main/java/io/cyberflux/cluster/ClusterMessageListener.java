package io.cyberflux.cluster;

import io.cyberflux.meta.cluster.ClusterMessage;

public interface ClusterMessageListener {
    void handle(ClusterMessage message);
}
