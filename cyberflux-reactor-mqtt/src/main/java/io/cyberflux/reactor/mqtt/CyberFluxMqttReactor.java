package io.cyberflux.reactor.mqtt;

import io.cyberflux.meta.reactor.AbstractReactiveServer;
import io.cyberflux.meta.reactor.ProtocolType;
import io.cyberflux.meta.reactor.ReactiveServer;
import io.cyberflux.reactor.mqtt.transport.AbstractMqttTransport;
import reactor.core.publisher.Mono;

public class CyberFluxMqttReactor extends AbstractReactiveServer {
    private AbstractMqttTransport transport;

    public CyberFluxMqttReactor() {
        super(ProtocolType.MQTT);
    }

    @Override
    public Mono<ReactiveServer> start() {
        return null;
    }

    @Override
    public void shutdown() {
        transport.dispose();
    }

    public static CyberFluxMqttReactor.MqttServerBuilder builder() {
        return new CyberFluxMqttReactor.MqttServerBuilder();
    }

    public static class MqttServerBuilder {
        public CyberFluxMqttReactor build() {
            return new CyberFluxMqttReactor();
        }
    }
}
