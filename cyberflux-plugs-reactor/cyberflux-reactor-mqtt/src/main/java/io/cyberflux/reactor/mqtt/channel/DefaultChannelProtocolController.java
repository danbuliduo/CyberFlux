package io.cyberflux.reactor.mqtt.channel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.cyberflux.reactor.mqtt.ack.MqttAcknowledgement;
import io.cyberflux.reactor.mqtt.cluster.MqttClusterCloseMessage;
import io.cyberflux.reactor.mqtt.cluster.MqttClusterPublishMessage;
import io.cyberflux.reactor.mqtt.codec.MqttRetainMessage;
import io.cyberflux.reactor.mqtt.codec.MqttSessionMessage;
import io.cyberflux.reactor.mqtt.codec.MqttSubTopicStore;
import io.cyberflux.reactor.mqtt.codec.MqttWillMessage;
import io.cyberflux.reactor.mqtt.exception.MqttQosLevelTypeException;
import io.cyberflux.reactor.mqtt.registry.MqttRetainMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttSessionMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttSubTopicRegistry;
import io.cyberflux.reactor.mqtt.utils.MqttMessageBuilder;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttConnectVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttPubAckMessage;
import io.netty.handler.codec.mqtt.MqttProperties.MqttPropertyType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.handler.codec.mqtt.MqttUnsubscribeMessage;
import io.netty.handler.codec.mqtt.MqttVersion;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.Connection;


public class DefaultChannelProtocolController implements MqttChannelProtocolController {

	/**
	 * @brief AUTH – 客户端增强认证
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
	@Override
	public Mono<Void> auth(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		return Mono.empty();
	}
	/**
	 * @brief CONNECT – 连接服务端
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    @Override
    public Mono<Void> connect(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		final MqttConnectMessage connMessage = (MqttConnectMessage) message;
		final MqttConnectPayload connPayload = connMessage.payload();
		final MqttConnectVariableHeader connVariableHeader = connMessage.variableHeader();
		final String clientIdentifier = connPayload.clientIdentifier();
		final byte version = (byte) connVariableHeader.version();
		// { MQTT 3.1.1 Version | MQTT 3.1 Version } & { MQTT 5.0 } 连接处理
		if(MqttVersion.MQTT_3_1_1.protocolLevel() == version || MqttVersion.MQTT_3_1.protocolLevel() == version) {
			// Client 身份认证
			if (context.authenticator.auth(connPayload.userName(), connPayload.passwordInBytes(), clientIdentifier)) {
				channel.bindIdentifier(clientIdentifier);
				context.inboundHandler.channelRegister(context, channel);

				channel.setCleanSession(connVariableHeader.isCleanSession());

				if (connVariableHeader.isWillFlag()) {
					channel.saveWillMessage(MqttWillMessage.fromConnectMessage(connMessage));
				}

				context.clusterReceiver.emitMessage(new MqttClusterCloseMessage(clientIdentifier));
				channel.registryDisposeEvent(that -> context.inboundHandler.channelInactive(context, channel));
				// Client 认证通过
				return channel.write(MqttMessageBuilder.buildConnAckMessage(
					MqttConnectReturnCode.CONNECTION_ACCEPTED
				)).then(Mono.fromRunnable(() -> {
					// 发布会话消息
					Optional.ofNullable(context.sessionRegistry.extract(clientIdentifier)).ifPresent(sessions -> {
						sessions.forEach(session -> {
							MqttPublishMessage pubMessage = session.toPublishMessage(channel);
							if(session.getLevel() == 0) {
								channel.write(pubMessage).subscribeOn(Schedulers.single()).subscribe();
							} else {
								channel.writeAndReply(pubMessage).subscribeOn(Schedulers.single()).subscribe();
							}
						});
					});
				}));
			}

			return channel.write(MqttMessageBuilder.buildConnAckMessage(
				MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD
			)).then(channel.close());

		} else if(MqttVersion.MQTT_5.protocolLevel() == version) {
			final MqttProperties properties = new MqttProperties();
			properties.add(new MqttProperties.IntegerProperty(MqttPropertyType.RETAIN_AVAILABLE.value(), 1));
			properties.add(new MqttProperties.IntegerProperty(MqttPropertyType.SHARED_SUBSCRIPTION_AVAILABLE.value(), 0));
			// Client 身份认证
			if (context.authenticator.auth(connPayload.userName(), connPayload.passwordInBytes(), clientIdentifier)) {
				channel.bindIdentifier(clientIdentifier);
				context.inboundHandler.channelRegister(context, channel);
				channel.setCleanSession(connVariableHeader.isCleanSession());

				if (connVariableHeader.isWillFlag()) {
					channel.saveWillMessage(MqttWillMessage.fromConnectMessage(connMessage));
				}

				channel.registryDisposeEvent(that -> context.inboundHandler.channelInactive(context, channel));
				context.clusterReceiver.emitMessage(new MqttClusterCloseMessage(clientIdentifier));

				return channel.write(MqttMessageBuilder.buildConnAckMessage(
					MqttConnectReturnCode.CONNECTION_ACCEPTED, properties
				)).then(Mono.fromRunnable(() -> {
					// 发布会话消息
					Optional.ofNullable(context.sessionRegistry.extract(clientIdentifier)).ifPresent(sessions -> {
						sessions.forEach(session -> {
							MqttPublishMessage pubMessage = session.toPublishMessage(channel);
							if (session.getLevel() == 0) {
								channel.write(pubMessage).subscribeOn(Schedulers.single()).subscribe();
							} else {
								channel.writeAndReply(pubMessage).subscribeOn(Schedulers.single()).subscribe();
							}
						});
					});
				}));
			}
			return channel.write(MqttMessageBuilder.buildConnAckMessage(
				MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD, properties
			)).then(channel.close());
		}
		// MQTT 不被支持的版本 拒绝连接
		return channel.write(MqttMessageBuilder.buildConnAckMessage(
			MqttConnectReturnCode.CONNECTION_REFUSED_UNSUPPORTED_PROTOCOL_VERSION
		)).then(channel.close());
    }

	/**
	 * @brief PUBLISH – 发布消息
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    @Override
    public Mono<Void> publish(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
        final MqttPublishMessage publishMessage = (MqttPublishMessage) message;
		final MqttPublishVariableHeader variableHeader = publishMessage.variableHeader();
		try {
			context.clusterReceiver.emitMessage(
				MqttClusterPublishMessage.fromPublishMessage(publishMessage, channel.channelId())
			);
			return switch (publishMessage.fixedHeader().qosLevel()) {
				case AT_MOST_ONCE ->
					Mono.when(InternalHandler.sendMessage(context.topicRegistry, context.sessionRegistry, publishMessage))
						.then(InternalHandler.filterRetainMessage(context.retainRegistry, publishMessage));
				case AT_LEAST_ONCE ->
					Mono.when(InternalHandler.sendMessage(context.topicRegistry, context.sessionRegistry, publishMessage))
						.then(channel.write(MqttMessageBuilder.buildPubAckMessage(variableHeader.packetId())))
						.then(InternalHandler.filterRetainMessage(context.retainRegistry, publishMessage));
				case EXACTLY_ONCE ->
					Mono.fromRunnable(() -> channel.saveQoS2Message(
						variableHeader.packetId(), MqttMessageBuilder.wrappedPublishMessage(
							publishMessage, MqttQoS.EXACTLY_ONCE, 0
						)
					)).then(channel.writeAndReply(MqttMessageBuilder.buildPubRecMessage(variableHeader.packetId())));
				case FAILURE -> {
					throw new MqttQosLevelTypeException("Unimplemented case: QosLevel Failure.");
				}
			};
		} catch (Exception e) {

		}
		return Mono.empty();
    }

	/**
	 * @brief PUBACK – 发布确认
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    @Override
    public Mono<Void> puback(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
        return Mono.fromRunnable(() -> {
			final MqttPubAckMessage pubAckMessage = (MqttPubAckMessage) message;
			final int messageId = pubAckMessage.variableHeader().messageId();
			Optional.ofNullable(context.acknowledgementManager.getAck(
				channel.generateId(MqttMessageType.PUBLISH, messageId)
			)).ifPresent(MqttAcknowledgement::stop);
		});
    }

	/**
	 * @brief PUBREC – 发布收到 (QoS 2, 第一步)
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    @Override
    public Mono<Void> pubrec(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		final int messageId = ((MqttMessageIdVariableHeader) message.variableHeader()).messageId();
        return Mono.fromRunnable(() -> {
			Optional.ofNullable(context.acknowledgementManager.getAck(
				channel.generateId(MqttMessageType.PUBLISH, messageId))
			).ifPresent(MqttAcknowledgement::stop);
		}).then(channel.writeAndReply(MqttMessageBuilder.buildPubRelMessage(messageId)));
    }

	/**
	 * @brief PUBREL – 发布释放 (QoS 2, 第二步)
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    @Override
    public Mono<Void> pubrel(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		final int messageId = ((MqttMessageIdVariableHeader) message.variableHeader()).messageId();
		return Optional.ofNullable(channel.removeQoS2Message(messageId)).map(pubmsg -> {
			return Mono.when(
				context.topicRegistry.findByTopic(pubmsg.variableHeader().topicName()).stream().filter(store -> {
					return  InternalHandler.filterSessionMessage(channel, context.sessionRegistry, pubmsg);
				}).map(store -> {
					final int level = Math.min(pubmsg.fixedHeader().qosLevel().value(), store.level());
					final MqttPublishMessage pubMessage = MqttMessageBuilder.wrappedPublishMessage(
						pubmsg, MqttQoS.valueOf(level), store.channel().generateMessageId()
					);
					return level == 0 ? store.channel().write(pubMessage) : store.channel().writeAndReply(pubMessage);
				}).collect(Collectors.toList())
			).then(Mono.fromRunnable(() -> {
				Optional.ofNullable(context.acknowledgementManager.getAck(
					channel.generateId(MqttMessageType.PUBREC, messageId))
				).ifPresent(MqttAcknowledgement::stop);
			})).then(channel.write(MqttMessageBuilder.buildPubCompMessage(messageId)));
		}).orElseGet(() -> channel.write(MqttMessageBuilder.buildPubCompMessage(messageId)));
    }

	/**
	 * @brief PUBCOMP – 发布完成 (QoS 2, 第三步)
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    @Override
    public Mono<Void> pubcomp(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		final int messageId = ((MqttMessageIdVariableHeader) message.variableHeader()).messageId();
        return Mono.fromRunnable(() -> {
			Optional.ofNullable(context.acknowledgementManager.getAck(
				channel.generateId(MqttMessageType.PUBREL, messageId))
			).ifPresent(MqttAcknowledgement::stop);
		});
    }

	/**
	 * @brief SUBSCRIBE –订阅主题
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    @Override
    public Mono<Void> subscribe(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
        final MqttSubscribeMessage subscribeMessage = (MqttSubscribeMessage) message;
        return Mono.fromRunnable(() -> {
			subscribeMessage.payload().topicSubscriptions().stream().peek(subscription -> {
				context.retainRegistry.findByTopic(subscription.topicName()).forEach(retain -> {
					if(retain.getLevel() == 0) {
						channel.write(retain.toPublishMessage(channel)).subscribe();
					} else {
						channel.writeAndReply(retain.toPublishMessage(channel)).subscribe();
					}
				});
			}).map(subscription -> {
				return new MqttSubTopicStore(
					channel, subscription.topicName(), subscription.qualityOfService()
				);
			}).collect(Collectors.toSet()).forEach(context.topicRegistry::append);
        }).then(channel.write(
			MqttMessageBuilder.buildSubAckMessage(
                subscribeMessage.variableHeader().messageId(),
                subscribeMessage.payload().topicSubscriptions().stream().map(subscription -> {
					return subscription.qualityOfService().value();
				}).collect(Collectors.toList())
			)
		));
    }

	/**
	 * @brief UNSUBSCRIBE – 取消订阅
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    @Override
    public Mono<Void> unsubscribe(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
        final MqttUnsubscribeMessage unsubscribeMessage = (MqttUnsubscribeMessage) message;
        return Mono.fromRunnable(() -> {
			unsubscribeMessage.payload().topics().stream().map(topic -> {
				return new MqttSubTopicStore(channel, topic, 0x00);
			}).forEach(context.topicRegistry::remove);
        }).then(channel.write(
			MqttMessageBuilder.buildUnsubAckMessage(unsubscribeMessage.variableHeader().messageId())
		));
    }

	/**
	 * @brief PINGRESP – 心跳响应
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
	@Override
	public Mono<Void> pingreq(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		return channel.write(MqttMessageBuilder.buildPingRespMessage());
	}

	/**
	 * @brief DISCONNECT – 断开连接服务端
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
	@Override
	public Mono<Void> disconnect(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		return Mono.fromRunnable(() -> {
			channel.clearWillMessage();
			final Connection connection;
			if (!(connection = channel.connection()).isDisposed()) {
				connection.dispose();
			}
		});
	}

	private class InternalHandler {
		/**
		 * @brief 发布消息
		 * @param topicRegistry   {@link MqttSubTopicRegistry}
		 * @param sessionRegistry {@link MqttSessionMessageRegistry}
		 * @param message         {@link MqttMessage}
		 * @return {@link Mono}
		 */
		public static List<Mono<Void>> sendMessage(
				MqttSubTopicRegistry topicRegistry,
				MqttSessionMessageRegistry sessionRegistry,
				MqttPublishMessage message) {
			return topicRegistry.findByTopic(
					message.variableHeader().topicName()
				).stream().filter(store -> {
					return filterSessionMessage(store.channel(), sessionRegistry, message);
				}).map(store -> {
					final int level = Math.min(message.fixedHeader().qosLevel().value(), store.level());
					final MqttPublishMessage pubMessage = MqttMessageBuilder.wrappedPublishMessage(
						message, MqttQoS.valueOf(level), store.channel().generateMessageId()
					);
					return level == 0 ? store.channel().write(pubMessage)
							: store.channel().writeAndReply(pubMessage);
				}).collect(Collectors.toList());
		}

		/**
		 * @brief 过滤保留消息
		 * @param retainRegistry {@link MqttRetainMessageRegistry}
		 * @param message        {@link MqttMessage}
		 * @return {@link Mono}
		 */
		public static Mono<Void> filterRetainMessage(
				MqttRetainMessageRegistry retainRegistry,
				MqttPublishMessage message) {
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
		public static boolean filterSessionMessage(
				MqttChannel channel,
				MqttSessionMessageRegistry sessionRegistry,
				MqttPublishMessage message) {
			if (channel.isOnline()) {
				return true;
			}
			sessionRegistry.append(
				MqttSessionMessage.fromPublishMessage(channel.channelId(), message)
			);
			return false;
		}
	}
}
