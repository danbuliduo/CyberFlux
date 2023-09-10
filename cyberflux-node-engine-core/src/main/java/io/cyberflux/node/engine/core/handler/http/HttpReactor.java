package io.cyberflux.node.engine.core.handler.http;

import io.cyberflux.common.utils.CyberNanoIdUtils;
import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.AbstractReactor;
import io.cyberflux.meta.reactor.cluster.CyberClusterMessagePublisher;
import io.cyberflux.meta.reactor.cluster.CyberClusterMessageReceiver;

public class HttpReactor extends AbstractReactor {

	public HttpReactor(HttpTransportConfig config) {
		super(CyberType.HTTP, CyberNanoIdUtils.randomNanoId());
		this.transport = new HttpTransport(config);
	}

	@Override
	public CyberClusterMessagePublisher publisher() {
		throw new UnsupportedOperationException("Unimplemented method 'publisher'");
	}

	@Override
	public CyberClusterMessageReceiver receiver() {
		throw new UnsupportedOperationException("Unimplemented method 'receiver'");
	}
}
