package io.cyberflux.reactor.mqtt;

import java.util.Optional;

import io.cyberflux.common.utils.CyberConfigLoaderUtils;
import io.cyberflux.common.utils.CyberNanoIdUtils;
import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.CyberClusterHandler;
import io.cyberflux.meta.reactor.CyberClusterMessagePublisher;
import io.cyberflux.meta.reactor.CyberClusterMessageReceiver;
import io.cyberflux.meta.reactor.TemplateReactor;
import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;
import io.cyberflux.reactor.mqtt.transport.MqttTransport;
import io.cyberflux.reactor.mqtt.transport.MqttTransportConfig;
import io.cyberflux.reactor.mqtt.transport.MqttTransportFactory;
import reactor.core.Disposable;

public final class MqttReactor extends TemplateReactor {

	private MqttChannelContext context;


    public MqttReactor() {
        this(MqttChannelContext.INSTANCE, MqttTransportFactory.defaultTransport());
    }

    public MqttReactor(MqttTransportConfig config) {
       this(MqttChannelContext.INSTANCE, MqttTransportFactory.createTransport(config));
    }

	public MqttReactor(
		MqttChannelContext context,
		MqttTransport<? extends Disposable, ? extends MqttTransportConfig> transport) {

		super(CyberType.MQTT, CyberNanoIdUtils.randomNanoId());
		this.context = Optional.ofNullable(context).orElse(MqttChannelContext.INSTANCE);
		transport.setContext(this.context);
		this.transport = transport;

	}

    public static MqttReactor.Builder builder() {
        return new MqttReactor.Builder();
    }

	@Override
	public CyberClusterMessagePublisher publisher() {
		return context.getClusterPublisher();
	}

	@Override
	public CyberClusterMessageReceiver receiver() {
		return context.getClusterReceiver();
	}

    public static final class Builder {
		private MqttTransport<? extends Disposable, ? extends MqttTransportConfig> transport;
		private MqttChannelContext context;

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

		public MqttReactor.Builder context(MqttChannelContext context) {
			this.context = context;
			return this;
		}

        public MqttReactor build() {
			return  Optional.ofNullable(transport).map(transport ->
				new MqttReactor(context, transport)
			).orElse(new MqttReactor());
        }
    }
}
