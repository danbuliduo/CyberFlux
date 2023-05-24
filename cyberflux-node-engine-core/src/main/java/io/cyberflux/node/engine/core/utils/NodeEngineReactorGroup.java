package io.cyberflux.node.engine.core.utils;

import java.util.Collection;

import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.CyberReactor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NodeEngineReactorGroup {
	int size();
	void appendReactor(CyberReactor reactor);
	void appendReactor(Collection<CyberReactor> reactors);
	void removeReactor();
	void removeReactor(CyberType type);
	void removeReactor(Collection<String> uuids);
	void removeReactor(String uuid);
	Flux<CyberReactor> findReactor();
	Flux<CyberReactor> findReactor(CyberType type);
	Flux<CyberReactor> findReactor(Collection<String> uuids);
	Mono<CyberReactor> findReactor(String uuid);
}
