package io.cyberflux.reactor.mqtt.channel;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import gnu.trove.map.hash.TIntObjectHashMap;
import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.TemplateChannel;
import io.cyberflux.reactor.mqtt.codec.MqttWillMessage;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

public final class MqttChannel extends TemplateChannel {
	private final Connection connection;
	private final AtomicInteger atomicCounter;

	private MqttWillMessage willMessage;
	private TIntObjectHashMap<MqttPublishMessage> messageCache;


	private Disposable closeDisposable;

    public MqttChannel(Connection connection) {
		super(CyberType.MQTT, connection.channel().id().asLongText());
		this.connection = connection;
		atomicCounter = new AtomicInteger(0);
		messageCache = new TIntObjectHashMap<>(0);
    }

	public Connection connection() {
		return connection;
	}

	public void saveWillMessage(MqttWillMessage willMessage) {
		this.willMessage = willMessage;
	}

	public boolean bindIdentifier(String identifier) {
		if(!identifier.isBlank()) {
			this.channelId = identifier;
			return true;
		}
		return false;
	}

	public void setDisposeEvent(Consumer<MqttChannel> consumer) {
		connection.onDispose(() -> consumer.accept(this));
	}


	public long generateId(MqttMessageType messageType, int messageId) {
		return (long) connection.channel().hashCode() << 32 | (long) messageType.value() << 28 | messageId << 4 >>> 4;
	}

	public int generateMessageId() {
		int messageId;
		while(messageCache.contains(messageId = atomicCounter.incrementAndGet())) {
			if(messageId >= 65535) {
				synchronized (this) {
					messageId = atomicCounter.incrementAndGet();
					if(messageId >= 65535) {
						atomicCounter.set(0);
					} else break;
				}
			}
		}
		return messageId;
	}

	public boolean appendMessage(int messageId, MqttPublishMessage message) {
		if(!containsMessage(messageId)) {
			messageCache.put(messageId, message);
			return true;
		}
		return false;
	}

	public void cancelDelayCloseEvent() {
		if (closeDisposable != null && !closeDisposable.isDisposed()) {
			closeDisposable.dispose();
		}
	}

	public void setDelayCloseEvent() {
		final Connection conn = this.connection;
		closeDisposable = Mono.fromRunnable(() -> {
			if (!conn.isDisposed()) {
				conn.dispose();
			}
		}).delaySubscription(
				Duration.ofSeconds(10)).subscribe();
	}

	public Mono<Void> close() {
		return Mono.fromRunnable(() -> {
			messageCache.clear();
			if (!connection.isDisposed()) {
				connection.dispose();
			}
		});
	}

	public boolean removeMessage(int messageId) {
		return messageCache.remove(messageId) != null;
	}
	public boolean containsMessage(int messageId) {
		return messageCache.contains(messageId);
	}

    public Flux<MqttMessage> receiveMessage() {
        return connection.inbound().receiveObject()
			.cast(MqttMessage.class)
			.filter(msg -> msg.decoderResult().isSuccess());
    }

    private Mono<Void> write(Mono<MqttMessage> messageMono) {
        if(connection.channel().isActive() && connection.channel().isWritable()) {
            return connection.outbound().sendObject(messageMono).then();
        }
        return Mono.empty();
    }

	private Mono<Void> write(Flux<MqttMessage> messageFlux) {
		if (connection.channel().isActive() && connection.channel().isWritable()) {
			return connection.outbound().sendObject(messageFlux).then();
		}
		return Mono.empty();
	}

    public Mono<Void> write(MqttMessage message) {
		return this.write(Mono.just(message));
    }

	public Mono<Void> write(MqttMessage... messages) {
		return this.write(Flux.fromArray(messages));
	}

	public Mono<Void> write(Iterable<MqttMessage> messages) {
		return this.write(Flux.fromIterable(messages));
	}

	public Mono<Void> write(Stream<MqttMessage> messages) {
		return this.write(Flux.fromStream(messages));
	}

	/*private static class MessageSink {

		public static MessageSink SINK = new MessageSink();

		public Mono<Void> sendMessage(MqttMessage message, MqttChannel channel, boolean isRetry) {
			return channel.write(Mono.just(message));
		}
	}*/

}
