package io.cyberflux.reactor.mqtt.channel;

import java.util.LinkedList;
import java.util.List;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import io.cyberflux.meta.channel.ChannelGroup;
import io.cyberflux.meta.lang.MetaObject;
import io.cyberflux.meta.lang.MetaType;
import reactor.core.publisher.Flux;

public final class MqttChannelGroup extends MetaObject implements ChannelGroup<MqttChannel> {

    private final LoadingCache<String, MqttChannel> cache;

    public MqttChannelGroup() {
        super(MetaType.MQTT);
        cache = Caffeine.newBuilder().build(key -> null);
    }

    public MqttChannelGroup(long maximum) {
        super(MetaType.MQTT);
        cache = Caffeine.newBuilder().maximumSize(maximum).build(key -> null);
    }

    @Override
    public Flux<MqttChannel> channelFlux() {
        return Flux.fromIterable(cache.asMap().values());
    }


    @Override
    public MqttChannel find(String id) {
        return cache.get(id);
    }

    @Override
    public List<MqttChannel> findAll() {
        return new LinkedList<>(cache.asMap().values());
    }

	@Override
    public void append(MqttChannel channel) {
        cache.put(channel.getClientId(), channel);
    }

	@Override
    public void remove(MqttChannel channel) {
        remove(channel.getClientId());
    }

	@Override
    public void remove(String id) {
        cache.invalidate(id);
    }

	@Override
	public int count() {
		return cache.asMap().size();
	}
}
