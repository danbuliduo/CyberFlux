package io.cyberflux.reactor.mqtt.channel;

import java.util.Optional;

import io.cyberflux.reactor.mqtt.exception.MqttMessageTypeException;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ReactorNetty;

public class DefaultChannelMessageHandler implements MqttChannelMessageHandler {
	private final static Logger log = LoggerFactory.getLogger(DefaultChannelMessageHandler.class);
    protected MqttChannelProtocolController controller;
	protected Scheduler scheduler;

    public DefaultChannelMessageHandler(Scheduler scheduler, MqttChannelProtocolController controller) {
		this.scheduler = Optional.ofNullable(scheduler).orElseGet(Schedulers::boundedElastic);
		this.controller = Optional.ofNullable(controller).orElseGet(DefaultChannelProtocolController::new);
    }

	@Override
	public void channelRead(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
		log.info("ThreadName: {} , {}", Thread.currentThread().getName(), message.fixedHeader().messageType());
		onChannelRead(context, channel, message)
			//.subscribeOn(Schedulers.newParallel("Th-R1"))
			.subscribe($ -> {
					// do something.
				},
				error -> {
					log.error(error.getMessage());
					if (message.fixedHeader().messageType() != MqttMessageType.PUBLISH
						&& message.fixedHeader().messageType() != MqttMessageType.PUBREL) {
						ReactorNetty.safeRelease(message.payload());
					}
				},
				() -> {
					if(message.fixedHeader().messageType() != MqttMessageType.PUBLISH
						&& message.fixedHeader().messageType() != MqttMessageType.PUBREL) {
						ReactorNetty.safeRelease(message.payload());
					}
				}
			);
	}

    private Mono<Void> onChannelRead(MqttChannelContext context, MqttChannel channel, MqttMessage message) {
        return switch (message.fixedHeader().messageType()) {
            case AUTH        -> controller.auth        (context, channel, message);
            case CONNECT     -> controller.connect     (context, channel, message);
            case PUBLISH     -> controller.publish     (context, channel, message);
            case PUBACK      -> controller.puback      (context, channel, message);
            case PUBREC      -> controller.pubrec      (context, channel, message);
            case PUBREL      -> controller.pubrel      (context, channel, message);
            case PUBCOMP     -> controller.pubcomp     (context, channel, message);
            case PINGREQ     -> controller.pingreq     (context, channel, message);
            case SUBSCRIBE   -> controller.subscribe   (context, channel, message);
            case UNSUBSCRIBE -> controller.unsubscribe (context, channel, message);
			case DISCONNECT  -> controller.disconnect  (context, channel, message);
            default -> {
				throw new MqttMessageTypeException("Unimplemented case: Unknown message type.");
			}
        };
    }
}
