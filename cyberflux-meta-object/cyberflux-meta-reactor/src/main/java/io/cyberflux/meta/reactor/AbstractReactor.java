package io.cyberflux.meta.reactor;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.meta.data.CyberObject;
import io.cyberflux.meta.data.CyberStatus;
import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.transport.CyberTransport;

public abstract class AbstractReactor extends CyberObject implements CyberReactor {
	private static final Logger log = LoggerFactory.getLogger(AbstractReactor.class);

    protected String uuid;
    protected CyberStatus status;
	protected CyberTransport transport;

	protected int port() {
		return transport.config().getPort();
	}

    public AbstractReactor(String uuid) {
        this(CyberType.EMPTY, uuid);
    }

    public AbstractReactor(CyberType type, String uuid) {
		super(type);
        this.uuid = uuid;
		this.status = CyberStatus.INVALID;
    }

	@Override
	public CyberTransport transport() {
		return transport;
	}

    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public CyberStatus status() {
        return status;
    }

    @Override
    public CyberReactor startAwait() {
        return start().block();
    }

	@Override
	public Mono<CyberReactor> start() {
		if(transport == null) {
			log.warn("Transport layer is empty. [uuid={}, type={}]", uuid(), type());
			return Mono.just(this);
		}
		return transport.start()
			.doOnError(error -> {
				this.status = CyberStatus.EXCEPTION;
				log.error(error.getMessage());
			}).doOnSuccess(trans -> {
				this.status = CyberStatus.OPERATION;
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
				this.status = CyberStatus.EXCEPTION;
				log.error(error.getMessage());
			}).doOnSuccess($ -> {
				this.status = CyberStatus.INVALID;
				log.info("INVALID >>> [uuid={}, type={}, port={}]", uuid(), type(), port());
			});
	}
}