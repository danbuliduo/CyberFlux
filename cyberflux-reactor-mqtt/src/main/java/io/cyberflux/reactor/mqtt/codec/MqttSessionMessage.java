package io.cyberflux.reactor.mqtt.codec;

public final class MqttSessionMessage {
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

    public MqttSessionMessage setTopic(String topic) {
        this.topic = topic;
        return this;
    }
    public MqttSessionMessage setLevel(int level) {
        this.level = level;
        return this;
    }
    public MqttSessionMessage setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }
}
