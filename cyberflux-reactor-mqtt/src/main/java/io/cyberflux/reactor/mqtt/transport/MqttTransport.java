package io.cyberflux.reactor.mqtt.transport;

import io.cyberflux.meta.reactor.core.ReactiveTransport;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;

public class MqttTransport implements ReactiveTransport {
    private MqttTransportType type;
    private DisposableServer server;

    @Override
    public Mono<ReactiveTransport> start() {
       return null;
    }

    public Mono<ReactiveTransport> bind() {
        return null;
    }

    @Override
    public Mono<Void> dispose() {
        return Mono.fromRunnable(() -> {
            server.dispose();
        });
    }
    public MqttTransportType type() {
        return type;
    }
}
