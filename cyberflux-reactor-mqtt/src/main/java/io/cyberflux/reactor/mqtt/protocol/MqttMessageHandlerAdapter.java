package io.cyberflux.reactor.mqtt.protocol;

import io.cyberflux.reactor.mqtt.channel.MqttChannel;
import io.cyberflux.reactor.mqtt.channel.MqttChannelGroup;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnAckVariableHeader;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

public class MqttMessageHandlerAdapter implements MqttMessageHandler {
    private MqttChannelGroup channelGroup = new MqttChannelGroup();

    @Override
    public Mono<Void> connect(MqttChannel channel, MqttMessage message) {

        MqttProperties properties = MqttProperties.NO_PROPERTIES;
        MqttConnAckMessage connAck = new MqttConnAckMessage(
            new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE, false, 0x02),
            new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED, false, properties)
        );
        return channel.write(Mono.just(connAck));
    }

    @Override
    public Mono<Void> disonnect(MqttChannel channel, MqttMessage message) {
        return Mono.fromRunnable(() -> {
            Connection conn;
            if(!(conn = channel.connection()).isDisposed()) {
                conn.dispose();;
            }
        });
    }

    @Override
    public Mono<Void> publish(MqttChannel channel, MqttMessage message) {
        MqttPublishMessage pubmsg =  (MqttPublishMessage) message;

        return switch (pubmsg.fixedHeader().qosLevel()) {
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
        return Mono.empty();
    }

    @Override
    public Mono<Void> subscribe(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> unsubscribe(MqttChannel channel, MqttMessage message) {
        return Mono.empty();
    }
}
