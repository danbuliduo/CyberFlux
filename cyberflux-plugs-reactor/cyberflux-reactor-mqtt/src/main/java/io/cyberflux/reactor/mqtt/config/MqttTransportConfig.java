package io.cyberflux.reactor.mqtt.config;


public class MqttTransportConfig {
    private int port = 1883;
    private MqttTransportType type = MqttTransportType.TCP;

    public int getPort() {
        return port;
    }
    public MqttTransportType getType() {
        return type;
    }

    public MqttTransportConfig setPort(int port) {
        this.port = port;
        return this;
    }
    public MqttTransportConfig setType(MqttTransportType type) {
        this.type = type;
        return this;
    }
}
