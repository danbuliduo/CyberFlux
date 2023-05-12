package io.cyberflux.meta.data;

public class CyberClusterMessage  {

	private final long timeStamp;
	private final Object payload;
	private final CyberClusterAction action;

	public CyberClusterMessage(Object payload) {
		this(payload, CyberClusterAction.PUSH);
	}


	public CyberClusterMessage(Object payload, CyberClusterAction action) {
		this.payload = payload;
		this.action = action;
		this.timeStamp = System.currentTimeMillis();
	}

	public long timeStamp() {
		return timeStamp;
	}

	public Object payload() {
		return payload;
	}

	public CyberClusterAction action() {
		return action;
	}

}
