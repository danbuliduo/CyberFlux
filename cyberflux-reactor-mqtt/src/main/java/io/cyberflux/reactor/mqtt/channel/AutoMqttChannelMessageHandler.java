package io.cyberflux.reactor.mqtt.channel;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.mqtt.MqttMessage;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ReactorNetty;

public class AutoMqttChannelMessageHandler implements MqttChannelMessageHandler {
    private final static Logger log = LoggerFactory.getLogger(AutoMqttChannelMessageHandler.class);
    protected MqttChannelGroup channelGroup;
    protected MqttChannelProtocolInterface protocol;
    protected Scheduler scheduler;

    public AutoMqttChannelMessageHandler(Scheduler scheduler, MqttChannelGroup channelGroup) {
        this.scheduler = Optional.ofNullable(scheduler).orElse(Schedulers.boundedElastic());
        this.channelGroup = Optional.ofNullable(channelGroup).orElse(new MqttChannelGroup());
        this.protocol = new MqttChannelProtocolHandler(this.channelGroup);
    }

    @Override
    public void channelMessage(MqttChannel channel, MqttMessage message) {
        (switch (message.fixedHeader().messageType()) {
            case AUTH        -> Mono.empty();
            case CONNECT     -> protocol.connect(channel, message);
            case DISCONNECT  -> protocol.disconnect(channel, message);
            case PUBLISH     -> protocol.publish(channel, message);
            case PUBACK      -> protocol.puback(channel, message);
            case PUBREC      -> protocol.pubrec(channel, message);
            case PUBREL      -> protocol.pubrel(channel, message);
            case PUBCOMP     -> protocol.pubcomp(channel, message);
            case PINGREQ     -> protocol.pingreq(channel, message);
            case SUBSCRIBE   -> protocol.subscribe(channel, message);
            case UNSUBSCRIBE -> protocol.unsubscribe(channel, message);
            default -> Mono.empty();
        }).subscribeOn(scheduler).subscribe(
            avoid -> {
            },
            error -> {
                log.error(error.getMessage());
                ReactorNetty.safeRelease(message.payload());
            },
            () -> ReactorNetty.safeRelease(message.payload())
        );
    }
}
