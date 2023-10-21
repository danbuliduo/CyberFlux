package io.cyberflux.meta.transport;

import reactor.core.publisher.Mono;

public interface Transport {
	TransportConfig config();
	Mono<Void> dispose();
    Mono<Transport> start();
}

