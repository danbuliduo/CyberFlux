package io.cyberflux.reactor.mqtt.channel;

import java.util.concurrent.TimeUnit;

import io.cyberflux.reactor.mqtt.ack.DefaultMqttAcknowledgementManager;
import io.cyberflux.reactor.mqtt.ack.MqttAcknowledgementManager;
import io.cyberflux.reactor.mqtt.cluster.MqttClusterHandler;
import io.cyberflux.reactor.mqtt.registry.DefaultRetainMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.DefaultSessionMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.DefaultTopicRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttRetainMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttSessionMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttSubTopicRegistry;
import io.cyberflux.reactor.mqtt.security.MqttAuthenticator;
import io.cyberflux.reactor.mqtt.security.MqttAuthenticatorFactory;

public class MqttChannelContext {
	public final static MqttChannelContext INSTANCE = new MqttChannelContext();

	protected MqttAcknowledgementManager acknowledgementManager;
	protected MqttAuthenticator authenticator;
	protected MqttChannelGroup channelGroup;
	protected MqttChannelInboundHandler inboundHandler;
	protected MqttRetainMessageRegistry retainRegistry;
	protected MqttSessionMessageRegistry sessionRegistry;
	protected MqttSubTopicRegistry topicRegistry;
	protected MqttClusterHandler clusterHandler;

	public MqttChannelContext() {
		acknowledgementManager = new DefaultMqttAcknowledgementManager(20, TimeUnit.MILLISECONDS, 50);
		authenticator = MqttAuthenticatorFactory.instance().provider(null).createAuthenticator();
		channelGroup = MqttChannelGroup.INTTCASE;
		inboundHandler = new DefaultChannelInboundHandler(null);
		retainRegistry = new DefaultRetainMessageRegistry();
		sessionRegistry = new DefaultSessionMessageRegistry();
		topicRegistry = new DefaultTopicRegistry();
		clusterHandler = new MqttClusterHandler(this);
	}

	public void applyChannel(MqttChannel channel) {
		channel.registryDelayCloseEvent();
		inboundHandler.channelActivate(this, channel);
	}

	public MqttAcknowledgementManager getAcknowledgementManager() {
		return acknowledgementManager;
	}
	public MqttClusterHandler getClusterHandler() {
		return clusterHandler;
	}

	public MqttAuthenticator getAuthenticator() {
		return authenticator;
	}

	public MqttChannelGroup getChannelGroup() {
		return channelGroup;
	}

	public MqttChannelInboundHandler getInboundHandler() {
		return inboundHandler;
	}

	public MqttRetainMessageRegistry getRetainRegistry() {
		return retainRegistry;
	}

	public MqttSessionMessageRegistry getSessionRegistry() {
		return sessionRegistry;
	}

	public MqttSubTopicRegistry getTopicRegistry() {
		return topicRegistry;
	}

}
