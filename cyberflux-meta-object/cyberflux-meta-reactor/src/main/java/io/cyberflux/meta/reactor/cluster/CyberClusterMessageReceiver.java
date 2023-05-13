package io.cyberflux.meta.reactor.cluster;

import io.cyberflux.meta.data.cluster.CyberClusterMessage;
import reactor.core.publisher.Flux;

public interface CyberClusterMessageReceiver {
	Flux<CyberClusterMessage> receive();
}
