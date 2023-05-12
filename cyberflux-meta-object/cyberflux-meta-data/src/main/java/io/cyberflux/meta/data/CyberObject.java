package io.cyberflux.meta.data;

public class CyberObject implements CyberInterface {
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
