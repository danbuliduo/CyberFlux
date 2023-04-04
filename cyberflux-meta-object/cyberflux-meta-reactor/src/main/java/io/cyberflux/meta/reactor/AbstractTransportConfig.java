package io.cyberflux.meta.reactor;

import io.cyberflux.meta.medium.MediumType;

public abstract class AbstractTransportConfig {
	protected MediumType type;
	protected int port;

	public AbstractTransportConfig(MediumType type) {
		this.type = type;
	}

	public AbstractTransportConfig(int port, MediumType type) {
		this.port = port;
		this.type = type;
	}

	public MediumType type() {
		return type;
	}

	public int port() {
		return port;
	}

	public void bind(int port) {
		this.port = port;
	}
}

