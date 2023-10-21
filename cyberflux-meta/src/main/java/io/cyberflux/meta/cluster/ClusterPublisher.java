package io.cyberflux.meta.cluster;

import reactor.core.publisher.Mono;

public interface ClusterPublisher {
	/**
	 *
	 * @return 节点名称
	 */
	Mono<Void> spreadMessage(ClusterMessage message);

	/**
	 *
	 * @return 节点名称
	 */
	Mono<Void> spreadMessage(ClusterMessage message, String name);
}
