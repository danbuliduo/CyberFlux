package io.cyberflux.meta.data.cluster;

import reactor.core.publisher.Flux;

public interface CyberClusterReceiver {
	/**
	 * @return 获取集群中的某个成员
	 */
	Flux<CyberClusterMessage> receiveMessage();
	/**
	 * @return 获取集群中的某个成员
	 */
	Flux<CyberClusterEvent> receiveEvent();
}
