package io.cyberflux.meta.reactor.transport;

import java.util.Optional;

import io.cyberflux.meta.data.CyberObject;
import io.cyberflux.meta.data.CyberType;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

public abstract class AbstractTransport<D extends Disposable, T extends TransportConfig>
		extends CyberObject implements CyberTransport {

	protected D disposable;
	protected T config;

	public AbstractTransport(T config) {
		this(CyberType.EMPTY, config);
	}

	public AbstractTransport(CyberType type, T config) {
		super(type);
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
