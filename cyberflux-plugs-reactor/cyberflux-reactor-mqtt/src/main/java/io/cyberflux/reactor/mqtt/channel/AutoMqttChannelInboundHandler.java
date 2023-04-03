package io.cyberflux.reactor.mqtt.channel;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.scheduler.Scheduler;


//import io.cyberflux.reactor.mqtt.protocol.MqttProtocolInterface;
//import io.cyberflux.reactor.mqtt.protocol.MqttProtocolHandler;

public class AutoMqttChannelInboundHandler extends AutoMqttChannelMessageHandler implements MqttChannelInboundHandler {
    private final static Logger log = LoggerFactory.getLogger(AutoMqttChannelInboundHandler.class);
    //private  MqttProtocolInterface messageHandler;
    private  MqttChannelGroup channelGroup;
   // private  Scheduler scheduler;

    public AutoMqttChannelInboundHandler(Scheduler scheduler) {
        super(scheduler, new MqttChannelGroup());
        this.channelGroup = new MqttChannelGroup();
        //this.messageHandler = new MqttProtocolHandler(channelGroup);
    }

    @Override
    public void channelRegister(MqttChannel channel) {
        channelGroup.append(channel);
        channel.receive().filter(msg -> msg.decoderResult().isSuccess())
            .subscribe(msg -> super.channelMessage(channel, msg));
        log.info("Register{}", channel.clientId());
    }

    @Override
    public void channelInactive(MqttChannel channel) {
        channelGroup.remove(channel);
        log.info("Inactive{}", channel.clientId());
    }
}
