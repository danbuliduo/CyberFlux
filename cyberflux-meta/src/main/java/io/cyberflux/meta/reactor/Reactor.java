package io.cyberflux.meta.reactor;

import io.cyberflux.meta.channel.Channel;
import io.cyberflux.meta.channel.ChannelContext;
import io.cyberflux.meta.lang.MetaAttribute;
import io.cyberflux.meta.transport.Transport;
import reactor.core.publisher.Mono;

public interface Reactor extends MetaAttribute {
    String uuid();
	ReactorStatus status();
	ReactorOption option();
	Transport transport();
	ChannelContext<? extends Channel> context();
	ReactorMessagePublisher publisher();
	ReactorMessageReceiver receiver();
    Mono<Reactor> start();
	Mono<Void> close();
	Reactor startAwait();
    void shutdown();
}
