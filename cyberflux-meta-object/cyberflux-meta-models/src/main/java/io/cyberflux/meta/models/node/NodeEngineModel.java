package io.cyberflux.meta.models.node;

import java.io.Serializable;

public class NodeEngineModel implements Serializable {

	private String id;
	private String role;
	private String name;
	private String namespace;
	private String version;

	public NodeEngineModel() {

	}

	public NodeEngineModel(String id, String role, String name, String namespace, String version) {
		this.id = id;
		this.role = role;
		this.name = name;
		this.namespace = namespace;
		this.version = version;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
