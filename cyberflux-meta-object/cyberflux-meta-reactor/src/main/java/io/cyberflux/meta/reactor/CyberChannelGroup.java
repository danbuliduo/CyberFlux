package io.cyberflux.meta.reactor;

public interface CyberChannelGroup<CHANNEL extends CyberChannel> {
	CHANNEL find(String channelId);
	void append(CHANNEL channel);
	void remove(CHANNEL channel);
	void remove(String channelId);
	int count();
}
