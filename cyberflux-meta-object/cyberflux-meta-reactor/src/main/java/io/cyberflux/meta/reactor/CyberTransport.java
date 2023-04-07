package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberInterface;
import reactor.core.publisher.Mono;

public interface CyberTransport extends CyberInterface {
	TransportConfig config();
	Mono<Void> dispose();
    Mono<CyberTransport> start();
}
