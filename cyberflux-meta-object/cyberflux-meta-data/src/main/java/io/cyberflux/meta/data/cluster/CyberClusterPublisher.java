package io.cyberflux.meta.data.cluster;

import reactor.core.publisher.Mono;

public interface CyberClusterPublisher {
	/**
	 *
	 * @return 节点名称
	 */
	Mono<Void> spreadMessage(CyberClusterMessage message);

	/**
	 *
	 * @return 节点名称
	 */
	Mono<Void> spreadMessage(CyberClusterMessage message, String name);
}
