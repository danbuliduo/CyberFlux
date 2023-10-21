package io.cyberflux.cluster;

import java.util.List;

import io.cyberflux.meta.cluster.ClusterPublisher;
import reactor.core.publisher.Mono;

public interface Cluster extends ClusterPublisher {
	String id();
    /**
     * @return 获取集群中的某个成员
     */
    ClusterMember member(String name);
    /**
     * @return 获取集群中所有的成员
     */
    List<ClusterMember> members();
	/**
	 *
	 * @return 节点名称
	 */
	Mono<Void> shutdown();
}
