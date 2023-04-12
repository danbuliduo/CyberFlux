package io.cyberflux.reactor.mqtt.channel;

import io.cyberflux.reactor.mqtt.registry.DefaultRetainMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.DefaultSessionMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.DefaultTopicRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttRetainMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttSessionMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttSubTopicRegistry;
import io.cyberflux.reactor.mqtt.security.MqttAuthenticator;
import io.cyberflux.reactor.mqtt.security.MqttAuthenticatorFactory;

public class MqttChannelContext {

	protected MqttAuthenticator authenticator;
	protected MqttChannelGroup channelGroup;
	protected MqttChannelInboundHandler inboundHandler;
	protected MqttRetainMessageRegistry retainRegistry;
	protected MqttSessionMessageRegistry sessionRegistry;
	protected MqttSubTopicRegistry topicRegistry;

	public MqttChannelContext() {
		authenticator = MqttAuthenticatorFactory.instance().provider(null).createAuthenticator();
		channelGroup = MqttChannelGroup.INTTCASE;
		inboundHandler = new DefaultChannelInboundHandler(null);
		retainRegistry = new DefaultRetainMessageRegistry();
		sessionRegistry = new DefaultSessionMessageRegistry();
		topicRegistry = new DefaultTopicRegistry();
	}

	public void applyChannel(MqttChannel channel) {
		channel.setDelayCloseEvent();
		inboundHandler.channelActivate(this, channel);
	}
}
