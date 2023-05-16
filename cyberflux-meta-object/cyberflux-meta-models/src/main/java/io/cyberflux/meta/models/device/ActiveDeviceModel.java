package io.cyberflux.meta.models.device;

import java.io.Serializable;

public class ActiveDeviceModel implements Serializable {
	private int port;
	private String id;
	private String name;
	private String host;

	public ActiveDeviceModel() {

	}

	public ActiveDeviceModel(int port, String id, String name, String host) {
		this.port = port;
		this.id = id;
		this.name = name;
		this.host = host;
	}



	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}

}
