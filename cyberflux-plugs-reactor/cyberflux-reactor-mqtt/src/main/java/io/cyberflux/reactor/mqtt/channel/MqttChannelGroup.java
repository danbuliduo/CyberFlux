package io.cyberflux.reactor.mqtt.channel;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class MqttChannelGroup {
    public static final MqttChannelGroup INTTCASE = new MqttChannelGroup();

    private final LoadingCache<String, MqttChannel> cache;

    public MqttChannelGroup() {
        cache = Caffeine.newBuilder().build(key -> {
            return null;
        });
    }

    public MqttChannel find(String channelId) {
        return cache.get(channelId);
    }

    public void append(MqttChannel channel) {
        cache.put(channel.channelId(), channel);
    }

    public void remove(MqttChannel channel) {
        remove(channel.channelId());
    }

    public void remove(String channelId) {
        cache.invalidate(channelId);
    }
}
