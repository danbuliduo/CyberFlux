package io.cyberflux.reactor.mqtt;

import io.cyberflux.common.utils.CyberConfigLoaderUtils;
import io.cyberflux.common.utils.CyberNanoIdUtils;
import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.TemplateReactor;
import io.cyberflux.reactor.mqtt.transport.MqttTransport;
import io.cyberflux.reactor.mqtt.transport.MqttTransportConfig;
import io.cyberflux.reactor.mqtt.transport.MqttTransportFactory;
import reactor.core.Disposable;

public final class MqttReactor extends TemplateReactor {

    public MqttReactor() {
        this(MqttTransportFactory.defaultTransport());
    }

    public MqttReactor(MqttTransportConfig config) {
       this(MqttTransportFactory.createTransport(config));
    }

	public MqttReactor(MqttTransport<? extends Disposable, ? extends MqttTransportConfig> transport) {
		super(CyberType.MQTT, CyberNanoIdUtils.randomNanoId());
		this.transport = transport;
	}



    public static MqttReactor.Builder builder() {
        return new MqttReactor.Builder();
    }

    public static final class Builder {
		private MqttTransport<? extends Disposable, ? extends MqttTransportConfig> transport;

        public <T extends MqttTransportConfig> MqttReactor.Builder config(String path, Class<T> clasz) {
            T config = CyberConfigLoaderUtils.autoLoad(path, clasz);
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
