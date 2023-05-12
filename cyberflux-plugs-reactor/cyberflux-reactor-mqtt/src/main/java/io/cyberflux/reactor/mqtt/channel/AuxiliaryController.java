package io.cyberflux.reactor.mqtt.channel;

import java.util.List;

import java.util.stream.Collectors;

import io.cyberflux.reactor.mqtt.codec.MqttRetainMessage;
import io.cyberflux.reactor.mqtt.codec.MqttSessionMessage;

import io.cyberflux.reactor.mqtt.registry.MqttRetainMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttSessionMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttSubTopicRegistry;
import io.cyberflux.reactor.mqtt.utils.MqttMessageBuilder;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;

import reactor.core.publisher.Mono;


public abstract class AuxiliaryController {
	/**
	 * @brief 发布消息
	 * @param topicRegistry   {@link MqttSubTopicRegistry}
	 * @param sessionRegistry {@link MqttSessionMessageRegistry}
	 * @param message         {@link MqttMessage}
	 * @return {@link Mono}
	 */
	protected List<Mono<Void>> pushPublishMessage(
			MqttSubTopicRegistry topicRegistry,
			MqttSessionMessageRegistry sessionRegistry,
			MqttPublishMessage message) {
		return topicRegistry.findByTopic(
			message.variableHeader().topicName()).stream().filter(store -> {
				return filterSessionMessage(store.channel(), sessionRegistry, message);
			}
		).map(store -> {
			final int level = Math.min(message.fixedHeader().qosLevel().value(), store.level());
			final MqttPublishMessage pubMessage = MqttMessageBuilder.wrappedPublishMessage(
				message, MqttQoS.valueOf(level), store.channel().generateMessageId()
			);
			return level == 0 ? store.channel().write(pubMessage) : store.channel().writeAndReply(pubMessage);
		}).collect(Collectors.toList());
	}

	/**
	 * @brief 过滤保留消息
	 * @param retainRegistry {@link MqttRetainMessageRegistry}
	 * @param message        {@link MqttMessage}
	 * @return {@link Mono}
	 */
	protected Mono<Void> filterRetainMessage(MqttRetainMessageRegistry retainRegistry, MqttPublishMessage message) {
		return message.fixedHeader().isRetain() ? Mono.fromRunnable(() -> {
			retainRegistry.save(MqttRetainMessage.fromPublishMessage(message));
		}) : Mono.empty();
	}

	/**
	 * @brief 过滤会话消息
	 * @param channel         {@link MqttChannel}
	 * @param sessionRegistry {@link MqttSessionMessageRegistry}
	 * @param message         {@link MqttMessage}
	 * @return {@link Boolean}
	 */
	protected boolean filterSessionMessage(
			MqttChannel channel, MqttSessionMessageRegistry sessionRegistry, MqttPublishMessage message) {
		if (channel.isOnline()) {
			return true;
		}
		sessionRegistry.append(MqttSessionMessage.fromPublishMessage(channel.channelId(), message));
		return false;
	}
}
