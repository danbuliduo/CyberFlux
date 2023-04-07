package io.cyberflux.meta.data;

public class CyberObject implements CyberInterface {
	protected static CyberType TYPE;

	public CyberObject() {
		TYPE = CyberType.EMPTY;
	}

	public CyberObject(CyberType type) {
		TYPE = type;
	}

	public final CyberType type() {
		return TYPE;
	}
}
