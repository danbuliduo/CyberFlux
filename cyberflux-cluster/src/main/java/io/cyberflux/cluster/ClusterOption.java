package io.cyberflux.cluster;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.Data;

@Data
public class ClusterOption {
	protected boolean enable = false;
	protected int port = 4096;
	protected String[] nodes;
	protected String name = "";
	protected String namespace = "default";

	public ClusterOption() {
		try {
			this.name = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
