package io.cyberflux.reactor.mqtt.cluster;


import java.util.Optional;

import io.cyberflux.meta.data.cluster.CyberClusterMessage;
import io.cyberflux.meta.reactor.cluster.CyberClusterMessagePublisher;
import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;
import io.cyberflux.reactor.mqtt.codec.MqttSessionMessage;
import io.cyberflux.reactor.mqtt.registry.MqttSubTopicRegistry;
import io.cyberflux.reactor.mqtt.utils.MqttMessageBuilder;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MqttClusterMessagePublisher implements CyberClusterMessagePublisher {

	private final MqttChannelContext context;

	public MqttClusterMessagePublisher(MqttChannelContext context) {
		this.context = context;
	}

	@Override
	public Mono<Void> publish(CyberClusterMessage clusterMessage) {
		if(clusterMessage instanceof MqttClusterPublishMessage clusterPublishMessage) {
			MqttPublishMessage message = clusterPublishMessage.toPublishMessage();
			MqttSubTopicRegistry topicRegistry = context.getTopicRegistry();
			return Flux.fromIterable(topicRegistry.findByTopic(
				message.variableHeader().topicName()
			)).filter(store -> {
				if(store.channel().isOnline()) { return true; }
				context.getSessionRegistry().append(
					MqttSessionMessage.fromPublishMessage(store.channel().channelId(), message)
				);
				return false;
			}).flatMap(store -> {
				final int level = Math.min(message.fixedHeader().qosLevel().value(), store.level());
				final MqttPublishMessage pubMessage = MqttMessageBuilder.wrappedPublishMessage(
					message, MqttQoS.valueOf(level), store.channel().generateMessageId()
				);
				return level == 0 ? store.channel().write(pubMessage): store.channel().writeAndReply(pubMessage);

			}).then();
		} else if(clusterMessage instanceof MqttClusterCloseMessage clusterCloseMessage) {
			Optional.ofNullable(
				context.getChannelGroup().find(clusterCloseMessage.getChannelId())
			).ifPresent(channel -> channel.close().subscribe());
		}
		return Mono.empty();
	}
}
