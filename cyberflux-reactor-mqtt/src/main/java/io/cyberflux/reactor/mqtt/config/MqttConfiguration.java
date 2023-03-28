package io.cyberflux.reactor.mqtt.config;

public class MqttConfiguration {
    public final static MqttConfiguration DEFAULT = new MqttConfiguration();

    private MqttTransportConfig transport;

    public MqttTransportConfig getTransport() {
        return transport;
    }
    public MqttConfiguration setTransport(MqttTransportConfig transport) {
        this.transport = transport;
        return this;
    }
}
