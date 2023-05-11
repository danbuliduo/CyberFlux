package io.cyberflux.reactor.mqtt.codec;

import java.util.Objects;

import io.cyberflux.reactor.mqtt.channel.MqttChannel;
import io.netty.handler.codec.mqtt.MqttQoS;

public final class MqttSubTopicStore {
    public final static String SEPARATOR = "/";
    public final static String SINGLE_WILDCARD = "+";
    public final static String MULTI_WILDCARD = "#";

    private final MqttChannel channel;
    private final String topic;
    private final int level;

	public static MqttSubTopicStore fromConstructor(
			MqttChannel channel, String topic, int level) {
		final MqttSubTopicStore store = new MqttSubTopicStore(channel, topic, level);
		return store;
	}

	public static MqttSubTopicStore fromConstructor(
			MqttChannel channel, String topic, MqttQoS qos) {
		final MqttSubTopicStore store = new MqttSubTopicStore(channel, topic, qos);
		return store;
	}

    public MqttSubTopicStore(MqttChannel channel, String topic, int level) {
        this.channel = channel;
        this.topic = topic;
        this.level = level;
    }

	public MqttSubTopicStore(MqttChannel channel, String topic, MqttQoS qos) {
		this.channel = channel;
		this.topic = topic;
		level = qos.value();
	}


	public boolean containWildcard() {
		return topic.contains(SINGLE_WILDCARD) || topic.contains(MULTI_WILDCARD);
	}

	public MqttChannel channel() {
		return channel;
	}

	public String topic() {
		return topic;
	}

	public int level() {
		return level;
	}

	public void linkChannel() {
		channel.getTopicStores().add(this);
	}
	public void unlinkChannel() {
		channel.getTopicStores().remove(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(topic, channel);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(this.getClass() != obj.getClass()) {
			return false;
		}
		MqttSubTopicStore that = (MqttSubTopicStore) obj;
		return Objects.equals(this.topic, that.topic)
			&& Objects.equals(this.channel, that.channel);
	}
}
