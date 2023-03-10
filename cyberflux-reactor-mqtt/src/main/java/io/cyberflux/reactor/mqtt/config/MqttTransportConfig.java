package io.cyberflux.reactor.mqtt.config;


public class MqttTransportConfig {
    private int port;

    public int getPort() {
        return port;
    }

    public MqttTransportConfig setPort(int port) {
        this.port = port;
        return this;
    }
}
