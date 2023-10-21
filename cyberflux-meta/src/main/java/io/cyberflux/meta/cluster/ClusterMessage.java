package io.cyberflux.meta.cluster;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClusterMessage implements Serializable  {

	protected Object payload;
	protected ClusterAction action;

	public ClusterMessage(ClusterAction action) {
		this(null, action);
	}

	public ClusterMessage(Object payload, ClusterAction action) {
		this.payload = payload;
		this.action = action;
	}

}
