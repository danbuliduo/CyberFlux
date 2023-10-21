package io.cyberflux.meta.reactor;

import io.cyberflux.meta.cluster.ClusterMessage;
import reactor.core.publisher.Flux;

public interface ReactorMessageReceiver {
	Flux<ClusterMessage> receive();
}
