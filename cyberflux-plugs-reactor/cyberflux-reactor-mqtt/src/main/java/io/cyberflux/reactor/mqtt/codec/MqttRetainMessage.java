package io.cyberflux.reactor.mqtt.codec;

public final class MqttRetainMessage {
    private String topic;
    private byte[] bytes;
    private int level;

    public String getTopic() {
        return topic;
    }
    public int getLevel() {
        return level;
    }
    public byte[] getBytes() {
        return bytes;
    }

    public MqttRetainMessage setTopic(String topic) {
        this.topic = topic;
        return this;
    }
    public MqttRetainMessage setLevel(int level) {
        this.level = level;
        return this;
    }
    public MqttRetainMessage setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }
}
