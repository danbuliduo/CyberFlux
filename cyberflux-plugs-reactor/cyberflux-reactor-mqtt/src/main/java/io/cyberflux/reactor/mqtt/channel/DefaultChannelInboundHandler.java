package io.cyberflux.reactor.mqtt.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.scheduler.Scheduler;

public class DefaultChannelInboundHandler extends DefaultChannelMessageHandler implements MqttChannelInboundHandler {
    private final static Logger log = LoggerFactory.getLogger(DefaultChannelInboundHandler.class);

    public DefaultChannelInboundHandler(Scheduler scheduler) {
		super(scheduler, new DefaultChannelProtocolController());
    }

	@Override
	public void channelActivate(MqttChannelContext context, MqttChannel channel) {
		channel.receiveMessage().subscribe(message -> channelRead(context, channel, message));
	}

    @Override
    public void channelRegister(MqttChannelContext context, MqttChannel channel) {
        log.info("Register{}", channel.channelId());
    }

    @Override
    public void channelInactive(MqttChannelContext context, MqttChannel channel) {
        log.info("Inactive{}", channel.channelId());
    }
}
