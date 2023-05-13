package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberInterface;
import io.cyberflux.meta.data.CyberStatus;
import io.cyberflux.meta.reactor.cluster.CyberClusterMessagePublisher;
import io.cyberflux.meta.reactor.cluster.CyberClusterMessageReceiver;
import io.cyberflux.meta.reactor.transport.CyberTransport;
import reactor.core.publisher.Mono;

public interface CyberReactor extends CyberInterface {
    String uuid();
	CyberStatus status();
	CyberTransport transport();
	CyberClusterMessagePublisher publisher();
	CyberClusterMessageReceiver receiver();
    Mono<CyberReactor> start();
	Mono<Void> close();
	CyberReactor startAwait();
    void shutdown();
}
