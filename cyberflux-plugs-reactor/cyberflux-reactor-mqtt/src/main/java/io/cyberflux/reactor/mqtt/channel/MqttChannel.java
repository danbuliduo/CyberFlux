package io.cyberflux.reactor.mqtt.channel;

import gnu.trove.map.hash.TIntObjectHashMap;
import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.TemplateChannel;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

public final class MqttChannel extends TemplateChannel {
    private final Connection connection;
	private final TIntObjectHashMap<MqttPublishMessage> messageCache;

    public MqttChannel(Connection connection) {
		super(CyberType.MQTT, connection.channel().id().asLongText());
        this.connection = connection;
		messageCache = new TIntObjectHashMap<>(0);
    }

    public Connection connection() {
        return connection;
    }

	public boolean appendMessage(int messageId, MqttPublishMessage message) {
		if(!containsMessage(messageId)) {
			messageCache.put(messageId, message);
			return true;
		}
		return false;
	}
	public boolean removeMessage(int messageId) {
		return messageCache.remove(messageId) != null;
	}
	public boolean containsMessage(int messageId) {
		return messageCache.contains(messageId);
	}

    public boolean isActive() {
        return connection != null && !connection.isDisposed();
    }

    public Flux<MqttMessage> receive() {
        return connection.inbound().receiveObject().cast(MqttMessage.class);
    }

    public Mono<Void> close() {
        return Mono.fromRunnable(() -> {

        });
    }

    public Mono<Void> write(Mono<MqttMessage> message) {
        if(connection.channel().isActive() && connection.channel().isWritable()) {
            return connection.outbound().sendObject(message).then();
        }
        return Mono.empty();
    }

    public Mono<Void> write(MqttMessage message) {
       return write(Mono.just(message));
    }
}
