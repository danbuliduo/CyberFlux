package io.cyberflux.meta.data;

public abstract class CyberMessage {
	protected int typeFlag;
	protected long timeFlag;

	public CyberMessage(int typeFlag) {
		this.typeFlag = typeFlag;
		this.timeFlag = System.currentTimeMillis();
	}

	public CyberMessage(CyberType type) {
		this.typeFlag =  type.getTypeFlag();
		this.timeFlag =  System.currentTimeMillis();
	}

	public int typeFlag() {
		return typeFlag;
	}

	public long timeFlag() {
		return timeFlag;
	}

}
