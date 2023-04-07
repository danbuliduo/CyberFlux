package io.cyberflux.reactor.mqtt.channel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.cyberflux.reactor.mqtt.codec.MqttTopicStore;
import io.cyberflux.reactor.mqtt.utils.MqttMessageBuilder;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.handler.codec.mqtt.MqttTopicSubscription;
import io.netty.handler.codec.mqtt.MqttUnsubscribeMessage;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;


public class DefaultChannelMqttProtocolController implements MqttChannelProtocolController {


	private List<Mono<Void>> publish(Set<MqttTopicStore> topicStores, MqttPublishMessage message) {
		return topicStores.stream()
				.map(store -> store.channel().write(message))
				.collect(Collectors.toList());
	}

	@Override
	public Mono<Void> auth(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
		return Mono.empty();
	}

    @Override
    public Mono<Void> connect(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
		context.channelGroup.append(channel);
        return channel.write(MqttMessageBuilder.buildConnAckMessage(MqttConnectReturnCode.CONNECTION_ACCEPTED));
    }

    @Override
    public Mono<Void> disconnect(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
        return Mono.fromRunnable(() -> {
            Connection connection;
            if(!(connection = channel.connection()).isDisposed()) {
                connection.dispose();
            }
        });
    }

    @Override
    public Mono<Void> publish(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
        MqttPublishMessage publishMessage =  (MqttPublishMessage) message;
		MqttPublishVariableHeader variableHeader = publishMessage.variableHeader();
        Set<MqttTopicStore> topicStoreSet = context.topicRegistry.findByTopic(variableHeader.topicName());
        return switch (publishMessage.fixedHeader().qosLevel()) {
            case AT_MOST_ONCE -> {
				yield Mono.when(this.publish(topicStoreSet, publishMessage))
					.then();
			}
            case AT_LEAST_ONCE -> {
				yield Mono.when(this.publish(topicStoreSet, publishMessage))
					.then(channel.write(MqttMessageBuilder.buildPubAckMessage(variableHeader.packetId())))
					.then();
			}
            case EXACTLY_ONCE -> {
				yield Mono.fromRunnable(() -> channel.appendMessage(variableHeader.packetId(), publishMessage))
					.then(channel.write(MqttMessageBuilder.buildPubRecMessage(variableHeader.packetId())));
			}
            default -> Mono.empty();
        };
    }

    @Override
    public Mono<Void> puback(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pubrec(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pubrel(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pubcomp(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pingreq(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
        return channel.write(MqttMessageBuilder.buildPingRespMessage());
    }

    @Override
    public Mono<Void> subscribe(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
        MqttSubscribeMessage subscribeMessage = (MqttSubscribeMessage) message;
        Stream<MqttTopicSubscription> topicStream = subscribeMessage.payload().topicSubscriptions().stream();
        return Mono.fromRunnable(() -> {
			Set<MqttTopicStore> topicStoreSet = topicStream.map(subscription ->
				MqttTopicStore.finalConstructor(channel, subscription.topicName(), subscription.qualityOfService())
			).collect(Collectors.toSet());
			if(!topicStoreSet.isEmpty()) {
				context.topicRegistry.appendAll(topicStoreSet);
			}
        }).then(channel.write(
			MqttMessageBuilder.buildSubAckMessage(
                subscribeMessage.variableHeader().messageId(),
                topicStream.map(subscription -> subscription.qualityOfService().value()).collect(Collectors.toList())
			)
		));
    }

    @Override
    public Mono<Void> unsubscribe(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
        MqttUnsubscribeMessage unsubscribeMessage = (MqttUnsubscribeMessage) message;
        return Mono.fromRunnable(() -> {
			unsubscribeMessage.payload().topics().stream()
				.map(topic -> MqttTopicStore.finalConstructor(channel, topic, 0x00))
				.forEach(context.topicRegistry::remove);
        }).then(channel.write(
			MqttMessageBuilder.buildUnsubAckMessage(unsubscribeMessage.variableHeader().messageId())
		));
    }
}
