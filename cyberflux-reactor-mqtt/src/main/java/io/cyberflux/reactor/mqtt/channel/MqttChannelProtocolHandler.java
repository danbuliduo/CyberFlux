package io.cyberflux.reactor.mqtt.channel;

//import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.cyberflux.reactor.mqtt.utils.MqttMessageBuilder;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
//import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.handler.codec.mqtt.MqttTopicSubscription;
import io.netty.handler.codec.mqtt.MqttUnsubscribeMessage;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

public class MqttChannelProtocolHandler implements MqttChannelProtocolInterface {
   // private  MqttChannelGroup channelGroup;

    public MqttChannelProtocolHandler(MqttChannelGroup channelGroup) {
        //this.channelGroup = channelGroup;
    }

    @Override
    public Mono<Void> connect(MqttChannel channel, MqttMessage message) {

        return channel.write(MqttMessageBuilder.buildConnAckMessage(
            MqttConnectReturnCode.CONNECTION_ACCEPTED
        ));
    }

    @Override
    public Mono<Void> disconnect(MqttChannel channel, MqttMessage message) {
        return Mono.fromRunnable(() -> {
            Connection conn;
            if(!(conn = channel.connection()).isDisposed()) {
                conn.dispose();
            }
        });
    }

    @Override
    public Mono<Void> publish(MqttChannel channel, MqttMessage message) {
        MqttPublishMessage publishMessage =  (MqttPublishMessage) message;

        return switch (publishMessage.fixedHeader().qosLevel()) {
            case AT_MOST_ONCE -> Mono.empty();
            case AT_LEAST_ONCE -> Mono.empty();
            case EXACTLY_ONCE -> Mono.empty();
            default -> Mono.empty();
        };
    }

    @Override
    public Mono<Void> puback(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pubrec(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pubrel(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pubcomp(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> pingreq(MqttChannel channel, MqttMessage message) {
        return channel.write(MqttMessageBuilder.buildPingRespMessage());
    }

    @Override
    public Mono<Void> subscribe(MqttChannel channel, MqttMessage message) {
        MqttSubscribeMessage subscribeMessage = (MqttSubscribeMessage) message;
        Stream<MqttTopicSubscription> topicStream = subscribeMessage.payload().topicSubscriptions().stream();
        return Mono.fromRunnable(() -> {

        }).then(channel.write(
            MqttMessageBuilder.buildSubAckMessage(
                subscribeMessage.variableHeader().messageId(),
                topicStream.map(sub -> sub.qualityOfService().value()).collect(Collectors.toList())
            )
        ));
    }

    @Override
    public Mono<Void> unsubscribe(MqttChannel channel, MqttMessage message) {
        MqttUnsubscribeMessage unsubscribeMessage = (MqttUnsubscribeMessage) message;
        return Mono.fromRunnable(() -> {

        }).then(channel.write(
            MqttMessageBuilder.buildUnsubAckMessage(
                unsubscribeMessage.variableHeader().messageId()
            )
        ));
    }
}
