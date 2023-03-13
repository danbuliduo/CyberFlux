package io.cyberflux.reactor.mqtt.channel;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.mqtt.MqttMessage;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ReactorNetty;

import io.cyberflux.reactor.mqtt.protocol.MqttProtocolInterface;
import io.cyberflux.reactor.mqtt.protocol.MqttProtocolHandler;

public class AutoMqttChannelInboundHandler extends AutoMqttChannelMessageHandler implements MqttChannelInboundHandler {
    private final static Logger log = LoggerFactory.getLogger(AutoMqttChannelInboundHandler.class);
    private  MqttProtocolInterface messageHandler;
    private  MqttChannelGroup channelGroup;
    //private  Scheduler scheduler;

    public AutoMqttChannelInboundHandler(Scheduler scheduler) {
        super(scheduler);
        this.channelGroup = new MqttChannelGroup();
        this.messageHandler = new MqttProtocolHandler(channelGroup);
    }

    @Override
    public void channelRegister(MqttChannel channel) {
        channelGroup.append(channel);
        channel.receive().filter(msg -> msg.decoderResult().isSuccess())
            .subscribe(msg -> super.channelMessage(channel, msg));
    }

    @Override
    public void channelInactive(MqttChannel channel) {
        channelGroup.remove(channel);
    }
}
