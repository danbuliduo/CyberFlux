package io.cyberflux.meta.reactor.channel;

import io.cyberflux.meta.data.CyberInterface;
import reactor.core.publisher.Mono;
public interface CyberChannel extends CyberInterface {
	String channelId();
	Mono<Void> close();
	boolean isOnline();
}
