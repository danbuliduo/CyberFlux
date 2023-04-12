package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberInterface;
import io.cyberflux.meta.data.CyberStatus;
import reactor.core.publisher.Mono;

public interface CyberReactor extends CyberInterface {
    String uuid();
    CyberStatus status();
    Mono<CyberReactor> start();
	Mono<Void> close();
	CyberReactor startAwait();
    void shutdown();
}
