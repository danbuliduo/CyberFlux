package io.cyberflux.reactor.mqtt.cluster;


import java.util.Optional;

import io.cyberflux.meta.cluster.ClusterMessage;
import io.cyberflux.meta.reactor.ReactorMessagePublisher;
import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;
import io.cyberflux.reactor.mqtt.codec.MqttSessionMessage;
import io.cyberflux.reactor.mqtt.registry.MqttSubscriptionRegistry;
import io.cyberflux.reactor.mqtt.utils.MqttMessageBuilder;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MqttReactorMessagePublisher implements ReactorMessagePublisher {

	private final MqttChannelContext context;

	public MqttReactorMessagePublisher(MqttChannelContext context) {
		this.context = context;
	}

	@Override
	public Mono<Void> publish(ClusterMessage clusterMessage) {
		if(clusterMessage instanceof MqttReactorPublishMessage clusterPublishMessage) {
			MqttPublishMessage message = clusterPublishMessage.toPublishMessage();
			MqttSubscriptionRegistry subscriptionRegistry = context.subscriptionRegistry();
			return Flux.fromIterable(subscriptionRegistry.findByTopic(
				message.variableHeader().topicName()
			)).filter(store -> {
				if(!store.getChannel().isOnline()) {
					context.sessionRegistry().append(
						MqttSessionMessage.fromPublishMessage(store.getChannel().getClientId(), message)
					);
					return false;
				}
				return true;
			}).flatMap(store -> {
				final int level = Math.min(message.fixedHeader().qosLevel().value(), store.getLevel());
				final MqttPublishMessage pubMessage = MqttMessageBuilder.wrappedPublishMessage(
					message, MqttQoS.valueOf(level), store.getChannel().generateMessageId()
				);
				return level == 0 ? store.getChannel().write(pubMessage): store.getChannel().writeAndReply(pubMessage);
			}).then();
		} else if(clusterMessage instanceof MqttReactorCloseMessage closeMessage) {
			Optional.ofNullable(
				context.channelGroup().find(closeMessage.getClientId())
			).ifPresent(channel -> channel.close().subscribe());
		}
		return Mono.empty();
	}
}
