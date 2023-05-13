package io.cyberflux.meta.cluster;

import java.util.List;

import io.cyberflux.meta.data.cluster.CyberClusterPublisher;
import io.cyberflux.meta.data.cluster.CyberClusterReceiver;
import reactor.core.publisher.Mono;

public interface CyberClusterNode extends CyberClusterPublisher, CyberClusterReceiver {
    /**
     * @return 获取集群中的某个成员
     */
    CyberClusterMember member(String name);
    /**
     * @return 获取集群中所有的成员
     */
    List<CyberClusterMember> members();
	/**
	 *
	 * @return 节点名称
	 */
	Mono<Void> shutdown();
}
