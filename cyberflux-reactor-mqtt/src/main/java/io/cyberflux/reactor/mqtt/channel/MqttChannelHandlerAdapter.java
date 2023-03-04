package io.cyberflux.reactor.mqtt.channel;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.mqtt.MqttMessage;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ReactorNetty;

import io.cyberflux.reactor.mqtt.protocol.MqttMessageHandler;
import io.cyberflux.reactor.mqtt.protocol.MqttMessageHandlerAdapter;

public class MqttChannelHandlerAdapter implements MqttChannelHandler {
    private final static Logger log = LoggerFactory.getLogger(MqttChannelHandlerAdapter.class);
    private final Scheduler scheduler;
    private final MqttMessageHandler messageHandler;
    private final MqttChannelGroup channelGroup;

    public MqttChannelHandlerAdapter(Scheduler scheduler) {
        this.scheduler = Optional.ofNullable(scheduler).orElse(Schedulers.boundedElastic());
        this.messageHandler = new MqttMessageHandlerAdapter();
        this.channelGroup = new MqttChannelGroup();
    }

    @Override
    public void onRegister(MqttChannel channel) {
        channelGroup.append(channel);
    }

    @Override
    public void onInactive(MqttChannel channel) {
        channelGroup.remove("");
    }

    @Override
    public void onMessage(MqttChannel channel, MqttMessage message) {
        (switch (message.fixedHeader().messageType()) {
            case AUTH        -> Mono.empty();
            case CONNECT     -> messageHandler.connect(channel, message);
            case DISCONNECT  -> messageHandler.disonnect(channel, message);
            case PUBLISH     -> messageHandler.publish(channel, message);
            case PUBACK      -> messageHandler.puback(channel, message);
            case PUBREC      -> messageHandler.pubrec(channel, message);
            case PUBREL      -> messageHandler.pubrel(channel, message);
            case PUBCOMP     -> messageHandler.pubcomp(channel, message);
            case PINGREQ     -> messageHandler.pingreq(channel, message);
            case SUBSCRIBE   -> messageHandler.subscribe(channel, message);
            case UNSUBSCRIBE -> messageHandler.unsubscribe(channel, message);
            default -> Mono.empty();
        }).subscribeOn(scheduler).subscribe(
            avoid -> {},
            error -> {
                log.error(error.getMessage());
                ReactorNetty.safeRelease(message.payload());
            },
            () -> ReactorNetty.safeRelease(message.payload())
        );
    }
}
