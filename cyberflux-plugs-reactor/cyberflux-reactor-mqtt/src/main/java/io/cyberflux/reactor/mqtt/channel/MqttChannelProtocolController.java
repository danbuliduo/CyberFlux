package io.cyberflux.reactor.mqtt.channel;

import io.netty.handler.codec.mqtt.MqttMessage;
import reactor.core.publisher.Mono;

/**
 * @author TengMing
 */
public interface MqttChannelProtocolController {
	/**
	 * @brief AUTH – 客户端认证
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
	Mono<Void> auth(MqttChannelContext context, MqttChannel channel, MqttMessage message);
	/**
	 * @brief CONNECT – 连接服务端
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    Mono<Void> connect(MqttChannelContext context, MqttChannel channel, MqttMessage message);

	/**
	 * @brief PUBLISH – 发布消息
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    Mono<Void> publish(MqttChannelContext context, MqttChannel channel, MqttMessage message);

	/**
	 * @brief PUBACK – 发布确认
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    Mono<Void> puback(MqttChannelContext context, MqttChannel channel, MqttMessage message);

	/**
	 * @brief PUBREC – 发布收到 (QoS 2, 第一步)
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    Mono<Void> pubrec(MqttChannelContext context, MqttChannel channel, MqttMessage message);

	/**
	 * @brief PUBREL – 发布释放 (QoS 2, 第二步)
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    Mono<Void> pubrel(MqttChannelContext context, MqttChannel channel, MqttMessage message);

	/**
	 * @brief PUBCOMP – 发布完成 (QoS 2, 第三步)
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    Mono<Void> pubcomp(MqttChannelContext context, MqttChannel channel, MqttMessage message);

	/**
	 * @brief SUBSCRIBE –订阅主题
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    Mono<Void> subscribe(MqttChannelContext context, MqttChannel channel, MqttMessage message);

	/**
	 * @brief UNSUBSCRIBE – 取消订阅
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
    Mono<Void> unsubscribe(MqttChannelContext context, MqttChannel channel, MqttMessage message);

	/**
	 * @brief PINGRESP – 心跳响应
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
	Mono<Void> pingreq(MqttChannelContext context, MqttChannel channel, MqttMessage message);

	/**
	 * @brief DISCONNECT – 断开连接服务端
	 * @param context {@link MqttChannelContext}
	 * @param channel {@link MqttChannel}
	 * @param message {@link MqttMessage}
	 * @return {@link Mono}
	 */
	Mono<Void> disconnect(MqttChannelContext context, MqttChannel channel, MqttMessage message);
}