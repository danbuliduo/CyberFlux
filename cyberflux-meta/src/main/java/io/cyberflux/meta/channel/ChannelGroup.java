package io.cyberflux.meta.channel;

import java.util.List;

import io.cyberflux.meta.lang.MetaAttribute;
import reactor.core.publisher.Flux;


public interface ChannelGroup<CHANNEL extends Channel> extends MetaAttribute {
	Flux<CHANNEL> channelFlux();
	List<CHANNEL> findAll();
	CHANNEL find(String id);
	void append(CHANNEL channel);
	void remove(CHANNEL channel);
	void remove(String id);
	int count();
}
