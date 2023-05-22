package io.cyberflux.node.engine.core.context;

import io.cyberflux.node.engine.core.config.CyberFluxNodeConfig;
import io.cyberflux.node.engine.core.handler.http.HttpReactor;
import io.cyberflux.node.engine.core.handler.http.HttpTransportConfig;

public class NodeEngineApplicationContext {

	private HttpReactor httpReactor;

	public NodeEngineApplicationContext(CyberFluxNodeConfig config) {
		httpReactor = new HttpReactor(new HttpTransportConfig());
		httpReactor.startAwait();
	}

}
