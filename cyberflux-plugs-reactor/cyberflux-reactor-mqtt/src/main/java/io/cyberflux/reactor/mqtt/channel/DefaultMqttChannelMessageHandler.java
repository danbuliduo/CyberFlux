package io.cyberflux.reactor.mqtt.channel;

import java.util.Optional;

import io.netty.handler.codec.mqtt.MqttMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ReactorNetty;

public class DefaultMqttChannelMessageHandler implements MqttChannelMessageHandler {
	private final static Logger log = LoggerFactory.getLogger(DefaultMqttChannelInboundHandler.class);
	private final Scheduler scheduler;
    private final MqttChannelProtocolController controller;

    public DefaultMqttChannelMessageHandler(Scheduler scheduler, MqttChannelProtocolController controller) {
		this.scheduler = Optional.ofNullable(scheduler).orElse(Schedulers.boundedElastic());
		this.controller = Optional.ofNullable(controller).orElse(MqttChannelProtocolController.INTTCASE);
    }

	@Override
	public void channelRead(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
		this.onMessageProcess(context, channel, message)
			.subscribeOn(scheduler)
			.subscribe($ -> {
					// do something.
				},
				error -> {
					log.error(error.getMessage());
					ReactorNetty.safeRelease(message.payload());
				},
				() -> ReactorNetty.safeRelease(message.payload()
			));
	}

    @Override
    public Mono<Void> onMessageProcess(MqttChannelHandlerContext context, MqttChannel channel, MqttMessage message) {
        return (switch (message.fixedHeader().messageType()) {
            case AUTH        -> controller.auth       (context, channel, message);
            case CONNECT     -> controller.connect    (context, channel, message);
            case DISCONNECT  -> controller.disconnect (context, channel, message);
            case PUBLISH     -> controller.publish    (context, channel, message);
            case PUBACK      -> controller.puback     (context, channel, message);
            case PUBREC      -> controller.pubrec     (context, channel, message);
            case PUBREL      -> controller.pubrel     (context, channel, message);
            case PUBCOMP     -> controller.pubcomp    (context, channel, message);
            case PINGREQ     -> controller.pingreq    (context, channel, message);
            case SUBSCRIBE   -> controller.subscribe  (context, channel, message);
            case UNSUBSCRIBE -> controller.unsubscribe(context, channel, message);
            default -> Mono.empty();
        });
    }
}
