package io.cyberflux.reactor.mqtt.channel;

import java.util.concurrent.TimeUnit;

import io.cyberflux.meta.channel.ChannelContext;
import io.cyberflux.meta.lang.MetaObject;
import io.cyberflux.meta.lang.MetaType;
import io.cyberflux.reactor.mqtt.ack.DefaultMqttAcknowledgementManager;
import io.cyberflux.reactor.mqtt.ack.MqttAcknowledgementManager;
import io.cyberflux.reactor.mqtt.cluster.MqttReactorMessagePublisher;
import io.cyberflux.reactor.mqtt.cluster.MqttReactorMessageReceiver;
import io.cyberflux.reactor.mqtt.registry.DefaultRetainMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.DefaultSessionMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.DefaultSubscriptionRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttRetainMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttSessionMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttSubscriptionRegistry;
import io.cyberflux.reactor.mqtt.security.MqttAuthenticator;
import io.cyberflux.reactor.mqtt.security.MqttAuthenticatorFactory;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import reactor.netty.resources.LoopResources;

public class MqttChannelContext extends MetaObject implements ChannelContext<MqttChannel> {

	//private final LoopResources loopResources;

	protected AbstractTrafficShapingHandler trafficShapingHandler;
	protected MqttAcknowledgementManager acknowledgementManager;
	protected MqttAuthenticator authenticator;
	protected MqttChannelGroup channelGroup;
	protected MqttChannelInboundHandler inboundHandler;
	protected MqttRetainMessageRegistry retainRegistry;
	protected MqttSessionMessageRegistry sessionRegistry;
	protected MqttSubscriptionRegistry subscriptionRegistry;

	protected MqttReactorMessagePublisher clusterPublisher;
	protected MqttReactorMessageReceiver clusterReceiver;

	private MqttChannelContext() {
		super(MetaType.MQTT);
		//loopResources = LoopResources.
		channelGroup = new MqttChannelGroup();
		//trafficShapingHandler = new GlobalTrafficShapingHandler();
		acknowledgementManager = new DefaultMqttAcknowledgementManager(20, TimeUnit.MILLISECONDS, 50);
		authenticator = MqttAuthenticatorFactory.instance().provider(null).createAuthenticator();
		inboundHandler = new DefaultChannelInboundHandler(null);
		retainRegistry = new DefaultRetainMessageRegistry();
		sessionRegistry = new DefaultSessionMessageRegistry();
		subscriptionRegistry = new DefaultSubscriptionRegistry();
		clusterPublisher = new MqttReactorMessagePublisher(this);
		clusterReceiver = new MqttReactorMessageReceiver();
	}

	public void applyChannel(MqttChannel channel) {
		channel.registryDelayCloseEvent();
		inboundHandler.channelActivate(this, channel);
	}

	public AbstractTrafficShapingHandler trafficShapingHandler() {
		return  trafficShapingHandler;
	}
	public MqttAcknowledgementManager acknowledgementManager() {
		return acknowledgementManager;
	}

	public MqttReactorMessagePublisher clusterPublisher() {
		return clusterPublisher;
	}

	public MqttReactorMessageReceiver clusterReceiver() {
		return clusterReceiver;
	}

	public MqttAuthenticator authenticator() {
		return authenticator;
	}

	public MqttChannelGroup channelGroup() {
		return channelGroup;
	}

	public MqttChannelInboundHandler inboundHandler() {
		return inboundHandler;
	}

	public MqttRetainMessageRegistry retainRegistry() {
		return retainRegistry;
	}

	public MqttSessionMessageRegistry sessionRegistry() {
		return sessionRegistry;
	}

	public MqttSubscriptionRegistry subscriptionRegistry() {
		return subscriptionRegistry;
	}

	public static MqttChannelContext instance() {
		return MqttChannelContext.Instance.INSTANCE;
	}

	private static class Instance {
		private static final MqttChannelContext INSTANCE = new MqttChannelContext();
	}
}
