package io.cyberflux.node.engine.core.handler.http;

import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.transport.TransportConfig;

public class HttpTransportConfig extends TransportConfig {
	public HttpTransportConfig() {
		super(CyberType.HTTP, 18087);
	}
}
