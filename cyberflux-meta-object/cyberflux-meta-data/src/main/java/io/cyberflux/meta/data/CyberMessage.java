package io.cyberflux.meta.data;

public abstract class CyberMessage {
	protected long timestamp;
	protected CyberType type;
	protected Object payload;

	public CyberMessage(CyberType type) {
		this.type = type;
		this.timestamp = System.currentTimeMillis();
	}

	public long grtTimeStamp() {
		return timestamp;
	}

	public CyberType getType() {
		return type;
	}

	public Object payload() {
		return payload;
	}

}
