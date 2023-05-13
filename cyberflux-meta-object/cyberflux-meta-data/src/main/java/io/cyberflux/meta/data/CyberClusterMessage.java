package io.cyberflux.meta.data;

import java.io.Serializable;

public abstract class CyberClusterMessage implements Serializable  {

	protected long timeStamp;
	protected Object payload;
	protected CyberClusterAction action;

	public CyberClusterMessage() {

	}

	public CyberClusterMessage(Object payload) {
		this(payload, CyberClusterAction.PUSH);
	}

	public CyberClusterMessage(CyberClusterAction action) {
		this(null, action);
	}

	public CyberClusterMessage(Object payload, CyberClusterAction action) {
		this.payload = payload;
		this.action = action;
		this.timeStamp = System.currentTimeMillis();
	}

	public long getTimeStamp() {
		return timeStamp;
	}


	public Object getPayload() {
		return payload;
	}


	public CyberClusterAction getAction() {
		return action;
	}


	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}


	public void setPayload(Object payload) {
		this.payload = payload;
	}


	public void setAction(CyberClusterAction action) {
		this.action = action;
	}

}
