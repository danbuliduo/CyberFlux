package io.cyberflux.meta.reactor.transport;

import io.cyberflux.meta.data.CyberObject;
import io.cyberflux.meta.data.CyberType;

public abstract class TransportConfig extends CyberObject {
	protected int port;

	public TransportConfig() {
		super(CyberType.EMPTY);
	}

	public TransportConfig(CyberType type, int port) {
		super(type);
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}

