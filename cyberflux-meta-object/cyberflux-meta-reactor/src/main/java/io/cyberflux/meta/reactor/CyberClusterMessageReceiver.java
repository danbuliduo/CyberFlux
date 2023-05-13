package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberClusterMessage;
import reactor.core.publisher.Flux;

public interface CyberClusterMessageReceiver {
	Flux<CyberClusterMessage> receive();
}
