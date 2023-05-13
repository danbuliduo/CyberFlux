package io.cyberflux.node.engine;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.node.engine.huaxu.annotation.CyberMetaObject;
import io.cyberflux.meta.data.CyberStatus;
import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.CyberReactor;
import io.cyberflux.meta.reactor.cluster.CyberClusterMessagePublisher;
import io.cyberflux.meta.reactor.cluster.CyberClusterMessageReceiver;
import io.cyberflux.meta.reactor.transport.CyberTransport;
import reactor.core.publisher.Mono;


@CyberMetaObject
public class CustomReactor implements CyberReactor {
    private static final Logger log = LoggerFactory.getLogger(CustomReactor.class);

    private String uuid = UUID.randomUUID().toString();
    private CyberType type =  CyberType.CUSTOM;

    public CustomReactor() {
        type.setField("CP-A");
    }

    @Override
    public CyberType type() {
        return this.type;
    }

    @Override
    public Mono<CyberReactor> start() {
        log.info("自定义Reactor:做点什么");
        return Mono.empty();
    }

    @Override
    public CyberReactor startAwait() {
        return start().block();
    }

    @Override
    public void shutdown() {
    }

    @Override
    public String uuid() {
        return uuid;
    }

    @Override
    public CyberStatus status() {
       return CyberStatus.OPERATION;
    }

	@Override
	public Mono<Void> close() {
		throw new UnsupportedOperationException("Unimplemented method 'stop'");
	}

	@Override
	public CyberTransport transport() {
		throw new UnsupportedOperationException("Unimplemented method 'transport'");
	}

	@Override
	public CyberClusterMessagePublisher publisher() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'publisher'");
	}

	@Override
	public CyberClusterMessageReceiver receiver() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'receiver'");
	}
}
