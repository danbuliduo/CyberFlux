package io.cyberflux.reactor.mqtt.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.scheduler.Scheduler;

public class DefaultMqttChannelInboundHandler extends DefaultMqttChannelMessageHandler
	 	implements MqttChannelInboundHandler {
    private final static Logger log = LoggerFactory.getLogger(DefaultMqttChannelInboundHandler.class);


    public DefaultMqttChannelInboundHandler(Scheduler scheduler) {
		super(scheduler, MqttChannelProtocolController.INTTCASE);
    }

	@Override
	public void channelActivate(MqttChannelHandlerContext context, MqttChannel channel) {
		channel.receive()
			.filter(msg -> msg.decoderResult().isSuccess())
			.subscribe(msg -> this.channelRead(context, channel, msg));
	}

    @Override
    public void channelRegister(MqttChannel channel) {
        log.info("Register{}", channel.channelId());
    }

    @Override
    public void channelInactive(MqttChannel channel) {
        log.info("Inactive{}", channel.channelId());
    }
}
