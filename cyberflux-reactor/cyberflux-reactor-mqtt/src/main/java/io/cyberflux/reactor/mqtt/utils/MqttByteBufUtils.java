package io.cyberflux.reactor.mqtt.utils;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.mqtt.MqttMessage;


public final class MqttByteBufUtils {

	public static void safeRelease(MqttMessage message) {
		if(message.payload() instanceof ByteBuf) {
			ByteBuf byteBuf = (ByteBuf) message.payload();
			if(byteBuf.refCnt() > 0) {
				byteBuf.release(byteBuf.refCnt());
			}
		}
	}
	public static void safeRelease(MqttMessage message, int decrement) {
		if (message.payload() instanceof ByteBuf && decrement > 0) {
			ByteBuf byteBuf = (ByteBuf) message.payload();
			byteBuf.release(decrement);
		}
	}

	public static byte[] copyByteBuf(ByteBuf byteBuf) {
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.resetReaderIndex().readBytes(bytes).resetReaderIndex();
		return bytes;
	}

	public static byte[] copyReleaseByteBuf(ByteBuf byteBuf) {
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes).resetReaderIndex();
		return bytes;
	}
}
