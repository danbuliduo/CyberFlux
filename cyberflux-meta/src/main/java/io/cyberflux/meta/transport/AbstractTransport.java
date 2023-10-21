package io.cyberflux.meta.transport;

import java.util.Optional;


import reactor.core.Disposable;
import reactor.core.publisher.Mono;

public abstract class AbstractTransport<D extends Disposable, T extends TransportConfig> implements Transport {

	protected D disposable;
	protected T config;

	public AbstractTransport(T config) {
		this.config = config;
		Runtime.getRuntime().addShutdownHook(new Thread(() ->
			Optional.ofNullable(disposable).ifPresent(D::dispose)
		));
	}

	@Override
    public Mono<Void> dispose() {
		return Mono.fromRunnable(() -> {
			disposable.dispose();
		});
	}

	@Override
	public T config() {
		return config;
	}
}
