package io.cyberflux.meta.cluster;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CyberClusterNode {
	/**
	 * @return 获取集群中的某个成员
	 */
	Flux<CyberClusterMessage> receiveMessage();
	/**
	 * @return 获取集群中的某个成员
	 */
	Flux<CyberClusterEvent>  receiveEvent();
    /**
     * @return 获取集群中的某个成员
     */
    CyberClusterMember member(String nodeName);
    /**
     * @return 获取集群中所有的成员
     */
    List<CyberClusterMember> members();

	/**
	 * 
	 * @return 节点名称
	 */
	Mono<Void> spreadMessage(CyberClusterMessage message);

	/**
	 * 
	 * @return 节点名称
	 */
	Mono<Void> spreadMessage(CyberClusterMessage message, String modeName);
	/**
	 * 
	 * @return 节点名称
	 */
	Mono<Void> shutdown();
}
