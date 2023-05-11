package io.cyberflux.meta.cluster;

public class CyberClusterMember {
	private int port;
	private String id;
	private String host;
	private String alias;
	private String namespace;

	public CyberClusterMember(String id,  String alias, String namespace, int port, String host) {
		this.id = id;
		this.port = port;
		this.host = host;
		this.alias = alias;
		this.namespace = namespace;
	}

	public int getPort() {
		return port;
	}
	public String getId() {
		return id;
	}

	public String getHost() {
		return host;
	}

	public String getAlias() {
		return alias;
	}
	public String getNamespace() {
		return namespace;
	}

	public static CyberClusterMember.Builder builder() {
		return new CyberClusterMember.Builder();
	}

	public static final class Builder {
		private int port;
		private String id;
		private String host;
		private String alias;
		private String namespace;

		public Builder port(int port) {
			this.port = port;
			return this;
		}
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		public Builder host(String host) {
			this.host = host;
			return this;
		}

		public Builder alias(String alias) {
			this.alias = alias;
			return this;
		}

		public Builder namespace(String namespace) {
			this.namespace = namespace;
			return this;
		}
		public CyberClusterMember build() {
			return new CyberClusterMember(id, alias, namespace, port, host);
		}
	}
}
