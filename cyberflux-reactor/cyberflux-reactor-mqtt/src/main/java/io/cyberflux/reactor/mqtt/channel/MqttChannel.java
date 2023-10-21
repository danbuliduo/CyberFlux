package io.cyberflux.reactor.mqtt.channel;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.cyberflux.meta.lang.MetaType;
import io.cyberflux.meta.channel.AbstractChannel;
import io.cyberflux.reactor.mqtt.ack.DefaultMqttAcknowledgement;
import io.cyberflux.reactor.mqtt.ack.MqttAcknowledgement;
import io.cyberflux.reactor.mqtt.ack.MqttAcknowledgementManager;
import io.cyberflux.reactor.mqtt.codec.MqttSubscriptionStore;
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

public class MqttChannel extends AbstractChannel {

	@JsonIgnore
	private final AtomicInteger atomicCounter;
	@JsonIgnore
	private final Connection connection;
	@JsonIgnore
	private final MqttPublishMessageRegistry messageCache;
	@JsonIgnore
	private final MqttAcknowledgementManager ackManager;
	@JsonIgnore
	private Disposable closeDisposable;
	@JsonIgnore
	private MqttWillMessage willMessage;
	@JsonIgnore
	private final Set<MqttSubscriptionStore> subscriptions;

	private boolean cleanSession;
	private byte version;

	public byte getVersion() {
		return version;
	}
	public void setVersion(byte version) {
		this.version = version;
	}
	public void setCleanSession(boolean value) {
		this.cleanSession = value;
	}
	public boolean isCleanSession() {
		return cleanSession;
	}

	public MqttChannel(Connection connection, MqttAcknowledgementManager ackManager) {
		super(MetaType.MQTT, connection.channel().id().asLongText());
		this.connection = connection;
		this.ackManager = ackManager;
		subscriptions = new CopyOnWriteArraySet<>();
		atomicCounter = new AtomicInteger(0);
		messageCache = new DefaultPublishMessageRegistry();
		this.setAddress(connection.address().toString().substring(1));
    }

	public Set<MqttSubscriptionStore> subscriptions() {
		return this.subscriptions;
	}


	public Connection connection() {
		return connection;
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


	public boolean bindClientId(String clientId) {
		if(!clientId.isBlank()) {
			this.clientId = clientId;
			return true;
		}
		return false;
	}

	public void setDisposeEvent(Consumer<MqttChannel> consumer) {
		connection.onDispose(() -> consumer.accept(this));
	}


	public long generateId(MqttMessageType messageType, int messageId) {
		return (long) connection.channel().hashCode() << 32 | (long) messageType.value() << 28 | (long) messageId << 4 >>> 4;
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
		if (message instanceof MqttPublishMessage publishMessage) {
			return publishMessage.copy().retain(Integer.MAX_VALUE >> 2);
		}
		return message;
	}

	private int getReplyMessageId(MqttMessage message) {
		Object object = message.variableHeader();
		if (object instanceof MqttPublishVariableHeader publishVariableHeader) {
			return publishVariableHeader.packetId();
		} else if (object instanceof MqttMessageIdVariableHeader messageIdVariableHeader) {
			return messageIdVariableHeader.messageId();
		} else {
			return -1; // client send connect key
		}
	}
}
