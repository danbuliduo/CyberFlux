package io.cyberflux.reactor.mqtt.codec;

import java.util.Objects;

import io.cyberflux.reactor.mqtt.channel.MqttChannel;
import io.netty.handler.codec.mqtt.MqttQoS;

public final class MqttSubscriptionStore {
    public final static String SEPARATOR = "/";
    public final static String SINGLE_WILDCARD = "+";
    public final static String MULTI_WILDCARD = "#";

    private MqttChannel channel;
    private String topic;
    private int level;

	public static MqttSubscriptionStore fromConstructor(MqttChannel channel, String topic, int level) {
		return new MqttSubscriptionStore(channel, topic, level);
	}

	public static MqttSubscriptionStore fromConstructor(MqttChannel channel, String topic, MqttQoS qos) {
		return new MqttSubscriptionStore(channel, topic, qos);
	}

	public MqttSubscriptionStore() {

	}

    public MqttSubscriptionStore(MqttChannel channel, String topic, int level) {
        this.channel = channel;
        this.topic = topic;
        this.level = level;
    }

	public MqttSubscriptionStore(MqttChannel channel, String topic, MqttQoS qos) {
		this.channel = channel;
		this.topic = topic;
		level = qos.value();
	}


	public boolean containWildcard() {
		return topic.contains(SINGLE_WILDCARD) || topic.contains(MULTI_WILDCARD);
	}

	public SimpleModel toSimpleModel() {
		return new SimpleModel(channel.getClientId(), topic, level);
	}


	public MqttChannel getChannel() {
		return channel;
	}

	public void setChannel(MqttChannel channel) {
		this.channel = channel;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void linkChannel() {
		channel.subscriptions().add(this);
	}
	public void unlinkChannel() {
		channel.subscriptions().remove(this);
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
		if(obj instanceof MqttSubscriptionStore that) {
			return Objects.equals(this.topic, that.topic) && Objects.equals(this.channel, that.channel);
		}
		return false;
	}

	public static class SimpleModel {
		private String clientId;
    	private String topic;
    	private int level;
		public SimpleModel(String clientId, String topic, int level) {
			this.clientId = clientId;
			this.topic = topic;
			this.level = level;
		}
		public String getClientId() {
			return clientId;
		}
		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
		public String getTopic() {
			return topic;
		}
		public void setTopic(String topic) {
			this.topic = topic;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		@Override
		public int hashCode() {
			return Objects.hash(topic, clientId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if(obj instanceof SimpleModel that) {
				return Objects.equals(this.topic, that.topic) && Objects.equals(this.clientId, that.clientId);
			}
			return false;
		}
	}
}
