package io.cyberflux.reactor.mqtt;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.common.utils.CyberConfigLoaderUtils;
import io.cyberflux.meta.medium.MediumType;
import io.cyberflux.meta.reactor.CyberFluxAbstractReactor;
import io.cyberflux.meta.reactor.CyberFluxReactor;
import io.cyberflux.meta.reactor.CyberFluxTransport;
import io.cyberflux.reactor.mqtt.config.MqttConfiguration;
import io.cyberflux.reactor.mqtt.transport.MqttTransport;
import io.cyberflux.reactor.mqtt.transport.MqttTransportFactory;

import reactor.core.publisher.Mono;

public class CyberFluxMqttReactor extends CyberFluxAbstractReactor {
    private static final Logger log = LoggerFactory.getLogger(CyberFluxMqttReactor.class);
    private MqttTransport transport;
    private MqttConfiguration config;

    public CyberFluxMqttReactor() {
        this(MqttConfiguration.DEFAULT);
    }

    public CyberFluxMqttReactor(MqttConfiguration config) {
        super(MediumType.MQTT);
        this.config = Optional.ofNullable(config).orElse(MqttConfiguration.DEFAULT);
    }

    private void bindTransport(CyberFluxTransport transport) {
        this.transport = (MqttTransport) transport;
    }

    @Override
    public Mono<CyberFluxReactor> start() {
        log.info("OPENING => [uuid:{} - type:{}]", uuid, type);
        return MqttTransportFactory.createTransport().start()
                .doOnError(Throwable::printStackTrace)
                .doOnSuccess(this::bindTransport)
                .thenReturn(this);
    }

    @Override
    public void shutdown() {
        log.info("CLOSING => [uuid:{} - type:{}]", uuid, type);
        transport.dispose();
    }

	public MqttConfiguration config() {
		return this.config;
	}

    public static CyberFluxMqttReactor.MqttReactorBuilder builder() {
        return new CyberFluxMqttReactor.MqttReactorBuilder();
    }

    public static class MqttReactorBuilder {
        private MqttConfiguration config;

        public CyberFluxMqttReactor.MqttReactorBuilder config(MqttConfiguration config) {
            this.config = config;
            return this;
        }

        public CyberFluxMqttReactor.MqttReactorBuilder config(String path) {
            this.config = CyberConfigLoaderUtils.autoLoad(path, MqttConfiguration.class);
            return this;
        }

        public CyberFluxMqttReactor build() {
            return new CyberFluxMqttReactor(config);
        }
    }
}
