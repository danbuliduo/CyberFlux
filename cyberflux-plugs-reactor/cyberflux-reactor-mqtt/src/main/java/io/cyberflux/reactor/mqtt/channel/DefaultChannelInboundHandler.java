package io.cyberflux.reactor.mqtt.channel;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.reactor.mqtt.codec.MqttSubTopicStore;
import reactor.core.scheduler.Scheduler;

public class DefaultChannelInboundHandler extends DefaultChannelMessageHandler implements MqttChannelInboundHandler {
    private final static Logger log = LoggerFactory.getLogger(DefaultChannelInboundHandler.class);

    public DefaultChannelInboundHandler(Scheduler scheduler) {
		super(scheduler, new DefaultChannelProtocolController());
    }

	/**
	 * 信道激活
	 */
	@Override
	public void channelActivate(MqttChannelContext context, MqttChannel channel) {
		channel.receiveMessage().subscribe(message -> channelRead(context, channel, message));
	}
	/**
	 * 信道注册
	 */
    @Override
    public void channelRegister(MqttChannelContext context, MqttChannel channel) {
		channel.cancelDelayCloseEvent();
		channel.setOnlineFlag(true);
		MqttChannelGroup channelGroup = context.channelGroup;
		Optional.ofNullable(channelGroup.find(channel.channelId())).ifPresent(that -> {
			that.getTopicStores().stream().map(store -> {
				return MqttSubTopicStore.fromConstructor(channel, store.topic(), store.level());
			}).collect(Collectors.toSet()).forEach(context.topicRegistry::append);
			channelGroup.remove(that);
			context.topicRegistry.removeAll(that.getTopicStores());

		});
		context.channelGroup.append(channel);
		log.debug("Register: {}, GroupSize: {}", channel.channelId(), context.channelGroup.count());
    }

	/**
	 * 信道关闭
	 */
    @Override
    public void channelInactive(MqttChannelContext context, MqttChannel channel) {
		channel.setOnlineFlag(false);
		Optional.ofNullable(channel.getWillMessage()).ifPresent(willmsg -> {
			context.topicRegistry.findByTopic(willmsg.getTopic()).forEach(store -> {
				if (store.level() == 0) {
					store.channel().write(willmsg.toPublishMessage(store.level(), 0)).subscribe();
				} else {
					final int messageId = store.channel().generateMessageId();
					store.channel().writeAndReply(willmsg.toPublishMessage(store.level(), messageId)).subscribe();
				}
			});
			channel.clearWillMessage();
		});
		if(channel.isCleanSession()) {
			context.topicRegistry.removeAll(channel.getTopicStores());
			context.channelGroup.remove(channel);
		}
		channel.close().subscribe();
		log.debug("Inactive: {}, GroupSize: {}", channel.channelId(), context.channelGroup.count());
    }
}
