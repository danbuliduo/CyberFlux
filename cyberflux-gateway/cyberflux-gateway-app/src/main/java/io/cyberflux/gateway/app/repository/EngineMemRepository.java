package io.cyberflux.gateway.app.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.cyberflux.cluster.ClusterOption;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import io.cyberflux.engine.spring.SpringEngine;
import io.cyberflux.gateway.app.codec.EngineEntity;
import io.cyberflux.gateway.app.codec.EngineEntity.Status;
import io.cyberflux.utils.net.HostUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class EngineMemRepository {

	private final Map<String, EngineEntity> options = new ConcurrentHashMap<>(3);

	public void save(EngineEntity engine) {
		options.put(engine.getHost(), engine);
	}

	public void remove(EngineEntity engine) {
		options.remove(engine.getHost());
	}


	public void removeAll() {
		options.clear();
	}

	public Flux<EngineEntity> findAll() {
		return Flux.fromIterable(options.values());
	}
	public Flux<EngineEntity> findByStatus(Status status) {
		return Flux.fromIterable(options.values()).filter(i -> i.getStatus() == status);
	}

	public Mono<EngineEntity> findByHost(String host) {
		return Mono.just(options.get(host));
	}

	public Mono<EngineEntity> findById(String id) {
		return Flux.fromIterable(options.values()).filter(item -> item.getId().equals(id)).last();
	}
}
