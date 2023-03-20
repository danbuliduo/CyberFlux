package io.cyberflux.reactor.mqtt;

import java.util.Optional;

import io.cyberflux.meta.reactor.CyberFluxAbstractReactor;
import io.cyberflux.meta.reactor.ReactorType;
import io.cyberflux.meta.reactor.CyberFluxReactor;
import io.cyberflux.meta.reactor.CyberFluxTransport;
import io.cyberflux.reactor.mqtt.config.MqttConfiguration;
import io.cyberflux.reactor.mqtt.transport.MqttTransport;
import io.cyberflux.reactor.mqtt.transport.MqttTransportFactory;
import reactor.core.publisher.Mono;

public class CyberFluxMqttReactor extends CyberFluxAbstractReactor {
    private MqttTransport transport;
    private MqttConfiguration config;

    public CyberFluxMqttReactor() {
        this(new MqttConfiguration());
    }

    public CyberFluxMqttReactor(MqttConfiguration config) {
        super(ReactorType.MQTT);
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
    public Mono<CyberFluxReactor> start() {
        return MqttTransportFactory.createTransport().start()
                .doOnError(Throwable::printStackTrace)
                .doOnSuccess(this::bindTransport)
                .thenReturn(this);
    }

    @Override
    public void shutdown() {
        transport.dispose();
    }

    private void bindTransport(CyberFluxTransport transport) {
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
