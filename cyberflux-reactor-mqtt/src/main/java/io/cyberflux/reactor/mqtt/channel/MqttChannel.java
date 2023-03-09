package io.cyberflux.reactor.mqtt.channel;

import io.netty.handler.codec.mqtt.MqttMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

public class MqttChannel {
    private final String clientId;
    private final Connection connection;

    public MqttChannel(Connection connection) {
        this.connection = connection;
        clientId = connection.channel().id().asLongText();
    }

    public String clientId() {
        return clientId;
    }
    public Connection connection() {
        return connection;
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
}
