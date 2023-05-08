package io.cyberflux.reactor.mqtt.channel;

import java.util.Optional;

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
        log.info("Register: {}", channel.channelId());
		context.channelGroup.append(channel);
    }

    @Override
    public void channelInactive(MqttChannelContext context, MqttChannel channel) {
        log.info("Inactive: {}", channel.channelId());
		System.out.println(channel.getWillMessage());
		Optional.ofNullable(channel.getWillMessage()).ifPresent(willmsg -> {
			context.topicRegistry.findByTopic(willmsg.getTopic()).forEach(store -> {
				if (store.level() == 0) {
					store.channel().write(willmsg.toPublishMessage(store.level(), 0));
				} else {
					final int messageId = store.channel().generateMessageId();
					store.channel().writeAndReply(willmsg.toPublishMessage(store.level(), messageId));
				}
			});
		});
		channel.clearWillMessage();
		channel.setChannelStatus(false);
		channel.close().subscribe();
    }
}
