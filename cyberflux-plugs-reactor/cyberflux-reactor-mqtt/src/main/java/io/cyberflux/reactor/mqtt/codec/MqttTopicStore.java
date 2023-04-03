package io.cyberflux.reactor.mqtt.codec;

import java.util.Objects;

import io.cyberflux.reactor.mqtt.channel.MqttChannel;

public final class MqttTopicStore {
    public final static String SEPARATOR = "/";
    public final static String SINGLE_WILDCARD = "+";
    public final static String MULTI_WILDCARD = "#";

    private final MqttChannel channel;
    private final String topic;
    private final byte level;

    public MqttTopicStore(MqttChannel channel, String topic, byte level) {
        this.channel = channel;
        this.topic = topic;
        this.level = level;
    }

    public MqttChannel channel() {
        return channel;
    }

    public String topic() {
        return topic;
    }

    public byte level() {
        return level;
    }

    public boolean containWildcard() {
        return topic.contains(SINGLE_WILDCARD) || topic.contains(MULTI_WILDCARD);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MqttTopicStore that = (MqttTopicStore) o;
        return Objects.equals(topic, that.topic);
    }
}
