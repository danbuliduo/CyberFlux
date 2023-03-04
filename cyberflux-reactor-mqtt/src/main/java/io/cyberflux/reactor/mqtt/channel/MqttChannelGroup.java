package io.cyberflux.reactor.mqtt.channel;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class MqttChannelGroup {
    private final LoadingCache<String, MqttChannel> cache;
    public MqttChannelGroup() {
        cache = Caffeine.newBuilder().build(key -> {return null;});
    }
    public MqttChannel find(String channelId) {
        return cache.get(channelId);
    }
    public void append(MqttChannel channel) {
        cache.put(channel.clientId(), channel);
    }
    public void remove(String channelId) {
        cache.invalidate(channelId);
    }
}
