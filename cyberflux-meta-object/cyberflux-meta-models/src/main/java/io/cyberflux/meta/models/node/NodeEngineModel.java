package io.cyberflux.meta.models.node;

import java.io.Serializable;

public class NodeEngineModel implements Serializable {

	private String id;
	private String name;
	private String namespace;

	public NodeEngineModel() {

	}

	public NodeEngineModel(String id, String name, String namespace) {
		this.id = id;
		this.name = name;
		this.namespace = namespace;
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
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}
