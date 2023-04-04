package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.medium.MediumType;
import io.cyberflux.meta.medium.MediumStatus;

public abstract class AbstractReactor implements Reactor {
	private static final Logger log = LoggerFactory.getLogger(AbstractReactor.class);

    protected String uuid;
	protected Transport transport;
    protected MediumStatus status;
    protected MediumType type;

    public AbstractReactor() {
        this(MediumType.EMPTY);
    }

    public AbstractReactor(MediumType type) {
        this(UUID.randomUUID().toString(), type);
    }

    public AbstractReactor(String uuid, MediumType type) {
        this.uuid = uuid;
        this.type = type;
        this.status = MediumStatus.INVALID;
    }

    @Override
    public final String uuid() {
        return uuid;
    }

    @Override
    public final MediumStatus status() {
        return status;
    }

    @Override
    public final MediumType type() {
        return type;
    }

    @Override
    public Reactor startAwait() {
        return start().block();
    }

	@Override
	public Mono<Reactor> start() {
		return transport.start()
				.doOnError(throwable -> {
					status = MediumStatus.EXCEPTION;
					log.error(throwable.toString());
				}).doOnSuccess(transport -> {
					status = MediumStatus.OPERATION;
					log.info("OPENING => [uuid:{} - type:{}]", uuid, type);
				}).thenReturn(this);
	}

	public void shutdown() {
		stop().block();
	}

	@Override
	public Mono<Void> stop() {
		return transport.dispose()
				.doOnError(throwable -> {
					status = MediumStatus.EXCEPTION;
					log.error(throwable.toString());
				}).doOnSuccess($ -> {
					status = MediumStatus.INVALID;
					log.info("INVALID => [uuid:{} - type:{}]", uuid, type);
				}).then();
	}
}