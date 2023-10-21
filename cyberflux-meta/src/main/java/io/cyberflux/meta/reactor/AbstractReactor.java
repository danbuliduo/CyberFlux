package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.lang.MetaObject;
import io.cyberflux.meta.lang.MetaType;
import io.cyberflux.meta.transport.Transport;



public abstract class AbstractReactor extends MetaObject implements Reactor {
	private static final Logger log = LoggerFactory.getLogger(AbstractReactor.class);

    protected String uuid;
    protected ReactorStatus status;
	protected ReactorOption option;
	protected Transport transport;

	protected int port() {
		return transport.config().getPort();
	}

    public AbstractReactor(String uuid) {
        this(MetaType.EMPTY, uuid);
    }

    public AbstractReactor(MetaType type, String uuid) {
		this(null, type, uuid);
    }

	public AbstractReactor(ReactorOption option, MetaType type, String uuid) {
		super(type);
		this.option = option;
		this.uuid = uuid;
		this.status = ReactorStatus.INVALID;
	}

	@Override
	public Transport transport() {
		return transport;
	}

	@Override
	public ReactorOption option() {
		return option;
	}

    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public ReactorStatus status() {
        return status;
    }

    @Override
    public Reactor startAwait() {
        return start().block();
    }

	@Override
	public Mono<Reactor> start() {
		if(transport == null) {
			log.warn("Transport layer is empty. [uuid={}, type={}]", uuid(), type());
			return Mono.just(this);
		}
		return transport.start()
			.doOnError(error -> {
				this.status = ReactorStatus.EXCEPTION;
				log.error(error.getMessage());
			}).doOnSuccess(trans -> {
				this.status = ReactorStatus.OPERATION;
				log.info("OPENING >>> [uuid={}, type={}, port={}]", uuid(), type(), port());
			}).thenReturn(this);
	}


	@Override
	public void shutdown() {
		close().block();
	}

	@Override
	public Mono<Void> close() {
		if (transport == null) {
			log.warn("Transport layer is empty. [uuid={}, type={}]", uuid(), type());
			return Mono.empty();
		}
		return transport.dispose()
			.doOnError(error -> {
				this.status = ReactorStatus.EXCEPTION;
				log.error(error.getMessage());
			}).doOnSuccess($ -> {
				this.status = ReactorStatus.INVALID;
				log.info("INVALID >>> [uuid={}, type={}, port={}]", uuid(), type(), port());
			});
	}
}