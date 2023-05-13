package io.cyberflux.meta.data;

public abstract class CyberObject {
	protected CyberType type;

	public CyberObject() {
		type = CyberType.EMPTY;
	}

	public CyberObject(CyberType type) {
		this.type = type;
	}

	public CyberType type() {
		return type;
	}
}
