package io.cyberflux.reactor.mqtt.transport;

public final class MqttTransportFactory {
    public static MqttTransport createTransport() {
        return new MqttTransport();
    }
}
