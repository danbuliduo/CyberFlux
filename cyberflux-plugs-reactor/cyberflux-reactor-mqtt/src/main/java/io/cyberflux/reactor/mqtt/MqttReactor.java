package io.cyberflux.reactor.mqtt;

import io.cyberflux.common.utils.CyberConfigLoaderUtils;
import io.cyberflux.meta.medium.MediumType;
import io.cyberflux.meta.reactor.AbstractReactor;
import io.cyberflux.reactor.mqtt.transport.MqttTransport;
import io.cyberflux.reactor.mqtt.transport.MqttTransportConfig;
import io.cyberflux.reactor.mqtt.transport.MqttTransportFactory;
import reactor.core.Disposable;

public final class MqttReactor extends AbstractReactor {

    public MqttReactor() {
        this(MqttTransportFactory.defaultTransport());
    }

    public MqttReactor(MqttTransportConfig config) {
       this(MqttTransportFactory.createTransport(config));
    }

	public MqttReactor(MqttTransport<? extends Disposable, ? extends MqttTransportConfig> transport) {
		super(MediumType.MQTT);
		this.transport = transport;
	}

    public static MqttReactor.Builder builder() {
        return new MqttReactor.Builder();
    }

    public static class Builder {
		private MqttTransport<? extends Disposable, ? extends MqttTransportConfig> transport;

        public <T extends MqttTransportConfig> MqttReactor.Builder config(String path, Class<T> clasz) {
            T config = CyberConfigLoaderUtils.auto(path, clasz);
			this.transport = MqttTransportFactory.createTransport(config);
            return this;
        }

		public MqttReactor.Builder config(MqttTransportConfig config) {
			this.transport = MqttTransportFactory.createTransport(config);
			return this;
		}

		public MqttReactor.Builder transport(
				MqttTransport<? extends Disposable, ? extends MqttTransportConfig> transport) {
			this.transport = transport;
			return this;
		}

        public MqttReactor build() {
			return transport == null ? new MqttReactor() : new MqttReactor(transport);
        }
    }
}
