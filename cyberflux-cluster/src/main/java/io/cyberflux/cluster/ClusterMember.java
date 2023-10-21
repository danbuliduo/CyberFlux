package io.cyberflux.cluster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClusterMember {
	private int port;
	private String id;
	private String host;
	private String name;
	private String namespace;
}
