package io.cyberflux.reactor.mqtt.channel;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.cyberflux.meta.data.CyberType;
import io.cyberflux.meta.reactor.channel.AbstractChannel;
import io.cyberflux.reactor.mqtt.ack.DefaultMqttAcknowledgement;
import io.cyberflux.reactor.mqtt.ack.MqttAcknowledgement;
import io.cyberflux.reactor.mqtt.ack.MqttAcknowledgementManager;
import io.cyberflux.reactor.mqtt.codec.MqttSubTopicStore;
import io.cyberflux.reactor.mqtt.codec.MqttWillMessage;
import io.cyberflux.reactor.mqtt.registry.DefaultPublishMessageRegistry;
import io.cyberflux.reactor.mqtt.registry.MqttPublishMessageRegistry;
import io.cyberflux.reactor.mqtt.utils.MqttByteBufUtils;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

public final class MqttChannel extends AbstractChannel {

	@JsonIgnore
	private final AtomicInteger atomicCounter;
	@JsonIgnore
	private final Connection connection;
	@JsonIgnore
	private final Set<MqttSubTopicStore> topicStores;
	@JsonIgnore
	private final MqttPublishMessageRegistry messageCache;
	@JsonIgnore
	private final MqttAcknowledgementManager ackManager;
	@JsonIgnore
	private Disposable closeDisposable;
	@JsonIgnore
	private MqttWillMessage willMessage;


	private boolean cleanSession;


    public MqttChannel(Connection connection, MqttAcknowledgementManager ackManager) {
		super(CyberType.MQTT, connection.channel().id().asLongText());
		this.connection = connection;
		this.ackManager = ackManager;
		topicStores = new CopyOnWriteArraySet<>();
		atomicCounter = new AtomicInteger(0);
		messageCache = new DefaultPublishMessageRegistry();
    }

	public Set<MqttSubTopicStore> getTopicStores() {
		return this.topicStores;
	}

	public boolean isCleanSession() {
		return cleanSession;
	}
	public void setCleanSession(boolean value) {
		this.cleanSession = value;
	}


	public Connection connection() {
		return connection;
	}

	public String address() {
		return connection.address().toString();
	}

	public void saveWillMessage(MqttWillMessage willMessage) {
		this.willMessage = willMessage;
	}

	public void clearWillMessage() {
		this.willMessage = null;
	}

	public MqttWillMessage getWillMessage() {
		return willMessage;
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
		while(containsQos2Message(messageId = atomicCounter.incrementAndGet())) {
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

	public boolean saveQoS2Message(int messageId, MqttPublishMessage message) {
		return messageCache.save(messageId, message);
	}

	public MqttPublishMessage removeQoS2Message(int messageId) {
		return messageCache.removeById(messageId);
	}

	public boolean containsQos2Message(int messageId) {
		return messageCache.contains(messageId);
	}

	public void cancelDelayCloseEvent() {
		if (closeDisposable != null && !closeDisposable.isDisposed()) {
			closeDisposable.dispose();
		}
	}

	public void registryDelayCloseEvent() {
		final Connection conn = this.connection;
		closeDisposable = Mono.fromRunnable(() -> {
			if (!conn.isDisposed()) {
				conn.dispose();
			}
		}).delaySubscription(Duration.ofSeconds(10)).subscribe();
	}

	public void registryDisposeEvent(Consumer<MqttChannel> consumer) {
		this.connection.onDispose(() -> consumer.accept(this));
	}

	@Override
	public Mono<Void> close() {
		return Mono.fromRunnable(() -> {
			messageCache.clear();
			if (!connection.isDisposed()) {
				connection.dispose();
			}
		});
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

	public Mono<Void> writeAndReply(MqttMessage message) {
		MqttMessage mqttMessage = getReplyMessage(message);
		Runnable runnable = () -> write(mqttMessage).subscribe();
		Runnable cleaner = () -> MqttByteBufUtils.safeRelease(mqttMessage);
		MqttAcknowledgement acknowledgement = new DefaultMqttAcknowledgement(
			generateId(message.fixedHeader().messageType(), getReplyMessageId(mqttMessage)),
			5, 5, runnable, cleaner, ackManager
		);
		acknowledgement.start();
		return this.write(message).then();
	}

	private MqttMessage getReplyMessage(MqttMessage message) {
		if (message instanceof MqttPublishMessage) {
			return ((MqttPublishMessage) message).copy().retain(Integer.MAX_VALUE >> 2);
		} else {
			return message;
		}
	}

	private int getReplyMessageId(MqttMessage message) {
		Object object = message.variableHeader();
		if (object instanceof MqttPublishVariableHeader) {
			return ((MqttPublishVariableHeader) object).packetId();
		} else if (object instanceof MqttMessageIdVariableHeader) {
			return ((MqttMessageIdVariableHeader) object).messageId();
		} else {
			return -1; // client send connect key
		}
	}
}
