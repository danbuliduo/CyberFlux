package io.cyberflux.reactor.mqtt.channel;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.reactor.mqtt.codec.MqttSubscriptionStore;
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
		channel.cancelDelayCloseEvent();
		channel.setOnline(true);
		MqttChannelGroup channelGroup = context.channelGroup;
		Optional.ofNullable(channelGroup.find(channel.getClientId())).ifPresent(that -> {
			that.subscriptions().stream().map(store -> {
				return MqttSubscriptionStore.fromConstructor(channel, store.getTopic(), store.getLevel());
			}).collect(Collectors.toSet()).forEach(context.subscriptionRegistry::append);
			channelGroup.remove(that);
			context.subscriptionRegistry.removeAll(that.subscriptions());

		});
		context.channelGroup.append(channel);
		log.debug("Register: {}, GroupSize: {}", channel.getClientId(), context.channelGroup.count());
    }

    @Override
    public void channelInactive(MqttChannelContext context, MqttChannel channel) {
		channel.setOnline(false);
		Optional.ofNullable(channel.getWillMessage()).ifPresent(willmsg -> {
			context.subscriptionRegistry.findByTopic(willmsg.getTopic()).forEach(store -> {
				if (store.getLevel() == 0) {
					store.getChannel().write(willmsg.toPublishMessage(store.getLevel(), 0)).subscribe();
				} else {
					final int messageId = store.getChannel().generateMessageId();
					store.getChannel().writeAndReply(willmsg.toPublishMessage(store.getLevel(), messageId)).subscribe();
				}
			});
			channel.clearWillMessage();
		});
		if(channel.isCleanSession()) {
			context.subscriptionRegistry.removeAll(channel.subscriptions());
			context.channelGroup.remove(channel);
		}
		channel.close().subscribe();
		log.debug("Inactive: {}, GroupSize: {}", channel.getClientId(), context.channelGroup.count());
    }
}
