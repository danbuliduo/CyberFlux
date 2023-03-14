package io.cyberflux.reactor.mqtt;

import java.util.Optional;

import io.cyberflux.meta.reactor.AbstractReactor;
import io.cyberflux.meta.reactor.ProtocolType;
import io.cyberflux.meta.reactor.Reactor;
import io.cyberflux.meta.reactor.Transport;
import io.cyberflux.reactor.mqtt.config.MqttConfiguration;
import io.cyberflux.reactor.mqtt.transport.MqttTransport;
import io.cyberflux.reactor.mqtt.transport.MqttTransportFactory;
import reactor.core.publisher.Mono;

public class CyberFluxMqttReactor extends AbstractReactor {
    private MqttTransport transport;
    private MqttConfiguration config;

    public CyberFluxMqttReactor() {
        this(new MqttConfiguration());
    }

    public CyberFluxMqttReactor(MqttConfiguration config) {
        super(ProtocolType.MQTT);
        this.config = Optional.ofNullable(config).orElse(new MqttConfiguration());
    }

    public CyberFluxMqttReactor handler() {
        return this;
    }

    public CyberFluxMqttReactor port(int port) {
        config.getTransport().setPort(port);
        return this;
    }

    @Override
    public Mono<Reactor> start() {
        return MqttTransportFactory.createTransport().start()
                .doOnError(Throwable::printStackTrace)
                .doOnSuccess(this::bindTransport)
                .thenReturn(this);
    }

    @Override
    public void shutdown() {
        transport.dispose();
    }

    private void bindTransport(Transport transport) {
        this.transport = (MqttTransport) transport;
    }

    public static CyberFluxMqttReactor.MqttReactorBuilder builder() {
        return new CyberFluxMqttReactor.MqttReactorBuilder();
    }

    public static class MqttReactorBuilder {
        public CyberFluxMqttReactor build() {
            return new CyberFluxMqttReactor();
        }
    }
}
