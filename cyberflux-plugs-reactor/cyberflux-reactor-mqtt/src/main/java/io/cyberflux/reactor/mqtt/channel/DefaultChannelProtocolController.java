package io.cyberflux.reactor.mqtt.channel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttProperties.MqttPropertyType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.handler.codec.mqtt.MqttUnsubscribeMessage;
import io.netty.handler.codec.mqtt.MqttVersion;

import reactor.core.publisher.Mono;
import reactor.netty.Connection;


public class DefaultChannelProtocolController implements MqttChannelProtocolController {

	private List<Mono<Void>> pushMessage(
			MqttSubTopicRegistry topicRegistry,
			MqttSessionMessageRegistry sessionRegistry,
			MqttPublishMessage message) {
		final int qosLevel = message.fixedHeader().qosLevel().value();
		return topicRegistry.findByTopic(
			message.variableHeader().topicName()
		).stream().filter(store -> {
			return filterSessionMessage(store.channel(), sessionRegistry, message);
		}).map(store -> {
			System.out.println("P-WRITW");
			return store.channel().write(MqttMessageBuilder.wrappedPublishMessage(
				message,
				MqttQoS.valueOf(Math.min(qosLevel, store.level())),
				store.channel().generateMessageId()
			));
		}).collect(Collectors.toList());
	}

	private boolean filterSessionMessage(
			MqttChannel channel, MqttSessionMessageRegistry sessionRegistry, MqttPublishMessage message) {
		if (!channel.isOnline()) {
			sessionRegistry.append(MqttSessionMessage.fromPublishMessage(channel.channelId(), message));
			return false;
		}
		return true;
	}

	private Mono<Void> filterRetainMessage(MqttRetainMessageRegistry retainRegistry, MqttPublishMessage message) {
		return message.fixedHeader().isRetain() ? Mono.fromRunnable(() -> {
			retainRegistry.save(MqttRetainMessage.fromPublishMessage(message));
		}) : Mono.empty();
	}

	@Override
	public Mono<Void> auth(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		return Mono.empty();
	}

    @Override
    public Mono<Void> connect(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		final MqttConnectMessage connMessage = (MqttConnectMessage) message;
		final MqttConnectPayload connPayload = connMessage.payload();
		final MqttConnectVariableHeader connVariableHeader = connMessage.variableHeader();
		final String clientIdentifier = connPayload.clientIdentifier();
		final byte version = (byte) connVariableHeader.version();

		if(MqttVersion.MQTT_3_1_1.protocolLevel() == version || MqttVersion.MQTT_3_1.protocolLevel() == version) {

			if (context.authenticator.auth(connPayload.userName(), connPayload.passwordInBytes(), clientIdentifier)) {
				channel.cancelDelayCloseEvent();
				channel.setChannelStatus(true);
				channel.bindIdentifier(clientIdentifier);
				if (connVariableHeader.isWillFlag()) {
					channel.saveWillMessage(MqttWillMessage.fromConnectMessage(connMessage));
				}
				context.channelGroup.append(channel);
				return channel.write(MqttMessageBuilder.buildConnAckMessage(
					MqttConnectReturnCode.CONNECTION_ACCEPTED
				));
			}
			return channel.write(MqttMessageBuilder.buildConnAckMessage(
				MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD
			)).then(channel.close());

		} else if(MqttVersion.MQTT_5.protocolLevel() == version) {
			final MqttProperties properties = new MqttProperties();
			properties.add(new MqttProperties.IntegerProperty(MqttPropertyType.RETAIN_AVAILABLE.value(), 1));
			properties.add(new MqttProperties.IntegerProperty(MqttPropertyType.SHARED_SUBSCRIPTION_AVAILABLE.value(), 0));

			if (context.authenticator.auth(connPayload.userName(), connPayload.passwordInBytes(), clientIdentifier)) {
				channel.bindIdentifier(clientIdentifier);
				if (connVariableHeader.isWillFlag()) {

				}
				channel.setChannelStatus(true);
				context.channelGroup.append(channel);
				return channel.write(MqttMessageBuilder.buildConnAckMessage(
					MqttConnectReturnCode.CONNECTION_ACCEPTED, properties
				));
			}
			return channel.write(MqttMessageBuilder.buildConnAckMessage(
				MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD, properties
			)).then(channel.close());
		}
		return channel.write(MqttMessageBuilder.buildConnAckMessage(
			MqttConnectReturnCode.CONNECTION_REFUSED_UNSUPPORTED_PROTOCOL_VERSION
		)).then(channel.close());
    }

    @Override
    public Mono<Void> publish(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
        final MqttPublishMessage publishMessage = (MqttPublishMessage) message;
		final MqttPublishVariableHeader variableHeader = publishMessage.variableHeader();
		try {
			return switch (publishMessage.fixedHeader().qosLevel()) {
				case AT_MOST_ONCE ->
					Mono.when(pushMessage(context.topicRegistry, context.sessionRegistry, publishMessage))
						.then(filterRetainMessage(context.retainRegistry, publishMessage));
				case AT_LEAST_ONCE ->
					Mono.when(pushMessage(context.topicRegistry, context.sessionRegistry, publishMessage))
						.then(channel.write(MqttMessageBuilder.buildPubAckMessage(variableHeader.packetId())))
						.then(filterRetainMessage(context.retainRegistry, publishMessage));
				case EXACTLY_ONCE ->
					Mono.fromRunnable(() -> channel.saveQoS2Message(variableHeader.packetId(), publishMessage))
						.then(channel.write(MqttMessageBuilder.buildPubRecMessage(variableHeader.packetId())));
				case FAILURE -> {
					throw new MqttQosLevelTypeException("Unimplemented case: QosLevel Failure.");
				}
			};
		} catch (Exception e) {

		}
		return Mono.empty();
    }

    @Override
    public Mono<Void> puback(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pubrec(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		final int messageId = ((MqttMessageIdVariableHeader) message.variableHeader()).messageId();
        return Mono.fromRunnable(() -> {

		}).then(channel.write(MqttMessageBuilder.buildPubRelMessage(messageId)));
    }

    @Override
    public Mono<Void> pubrel(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		final int messageId = ((MqttMessageIdVariableHeader) message.variableHeader()).messageId();
		return Optional.ofNullable(channel.removeQoS2Message(messageId)).map(pubmsg -> {
			int qosLevel = pubmsg.fixedHeader().qosLevel().value();
			System.out.println("OK1");
			System.out.println(pubmsg.payload());
			return Mono.when(
				context.topicRegistry.findByTopic(pubmsg.variableHeader().topicName()).stream().map(store -> {
						System.out.println("OK2");
					return store.channel().write(MqttMessageBuilder.wrappedPublishMessage(
						pubmsg,
						MqttQoS.valueOf(Math.min(qosLevel, store.level())),
						store.channel().generateMessageId())
					);
				}).collect(Collectors.toList())
			).then(

			).then(channel.write(MqttMessageBuilder.buildPubCompMessage(messageId)));
		}).orElseGet(() -> channel.write(MqttMessageBuilder.buildPubCompMessage(messageId)));
    }

    @Override
    public Mono<Void> pubcomp(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		final int messageId = ((MqttMessageIdVariableHeader) message.variableHeader()).messageId();
        return Mono.fromRunnable(() -> {

		});
    }

    @Override
    public Mono<Void> subscribe(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
        final MqttSubscribeMessage subscribeMessage = (MqttSubscribeMessage) message;
        return Mono.fromRunnable(() -> {
			subscribeMessage.payload().topicSubscriptions().stream().map(subscription -> {
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

	@Override
	public Mono<Void> pingreq(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		return channel.write(MqttMessageBuilder.buildPingRespMessage());
	}

	@Override
	public Mono<Void> disconnect(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		return Mono.fromRunnable(() -> {
			final Connection connection;
			if (!(connection = channel.connection()).isDisposed()) {
				connection.dispose();
			}
		});
	}
}
