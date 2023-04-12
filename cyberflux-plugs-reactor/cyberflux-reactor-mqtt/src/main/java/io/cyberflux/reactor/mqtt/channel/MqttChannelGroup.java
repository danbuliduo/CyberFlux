package io.cyberflux.reactor.mqtt.channel;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import io.cyberflux.meta.reactor.CyberChannelGroup;

public final class MqttChannelGroup implements CyberChannelGroup<MqttChannel> {
    public static final MqttChannelGroup INTTCASE = new MqttChannelGroup();

    private final LoadingCache<String, MqttChannel> cache;

    public MqttChannelGroup() {
        cache = Caffeine.newBuilder().build(key -> {
            return null;
        });
    }

	@Override
    public MqttChannel find(String channelId) {
        return cache.get(channelId);
    }

	@Override
    public void append(MqttChannel channel) {
        cache.put(channel.channelId(), channel);
    }

	@Override
    public void remove(MqttChannel channel) {
        remove(channel.channelId());
    }

	@Override
    public void remove(String channelId) {
        cache.invalidate(channelId);
    }

	@Override
	public int count() {
		return cache.asMap().size();
	}
}
