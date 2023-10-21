package io.cyberflux.reactor.mqtt;

import java.util.Optional;

import io.cyberflux.utils.id.NanoIdUtils;
import io.cyberflux.utils.io.FileLoaderUtils;
import io.cyberflux.meta.lang.MetaType;
import io.cyberflux.meta.reactor.AbstractReactor;
import io.cyberflux.meta.reactor.ReactorMessagePublisher;
import io.cyberflux.meta.reactor.ReactorMessageReceiver;
import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;
import io.cyberflux.reactor.mqtt.transport.MqttTransportFactory;

public final class MqttReactor extends AbstractReactor {

	private MqttChannelContext context;

    public MqttReactor() {
        this(MqttReactorOption.defaultOption());
	}

	public MqttReactor(MqttReactorOption option) {
		this(MqttChannelContext.instance(), option);
	}

	public MqttReactor(MqttChannelContext context, MqttReactorOption option) {
		super(option, MetaType.MQTT, NanoIdUtils.randomNanoId());
		this.context = Optional.ofNullable(context).orElse(MqttChannelContext.instance());
		this.transport = MqttTransportFactory.createTransport(option).setContext(context);
	}

    public static MqttReactor.Builder builder() {
        return new MqttReactor.Builder();
    }

	@Override
	public MqttChannelContext context() {
		return this.context;
	}

	@Override
	public ReactorMessagePublisher publisher() {
		return context.clusterPublisher();
	}

	@Override
	public ReactorMessageReceiver receiver() {
		return context.clusterReceiver();
	}

    public static final class Builder {
		private MqttReactorOption option;
		private MqttChannelContext context;

        public MqttReactor.Builder option(String path) {
            this.option = FileLoaderUtils.autoLoad(path, MqttReactorOption.class);
            return this;
        }

		public MqttReactor.Builder option(MqttReactorOption option) {
			this.option = option;
			return this;
		}

		public MqttReactor.Builder context(MqttChannelContext context) {
			this.context = context;
			return this;
		}

        public MqttReactor build() {
			return Optional.ofNullable(this.option)
				.map(transport -> new MqttReactor(context, transport))
				.orElse(new MqttReactor());
        }
    }
}
