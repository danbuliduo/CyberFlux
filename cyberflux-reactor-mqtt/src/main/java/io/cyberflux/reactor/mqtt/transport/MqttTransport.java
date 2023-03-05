package io.cyberflux.reactor.mqtt.transport;


import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import io.cyberflux.meta.reactor.ReactiveTransport;
import io.cyberflux.meta.reactor.core.AbstractReactiveTransport;

public class MqttTransport extends AbstractReactiveTransport {
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
}
