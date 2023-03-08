package io.cyberflux.reactor.mqtt.transport;


import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import io.cyberflux.meta.reactor.AbstractReactiveTransport;
import io.cyberflux.meta.reactor.ReactiveTransport;

public abstract class AbstractMqttTransport extends AbstractReactiveTransport {
    private DisposableServer server;

    @Override
    public Mono<ReactiveTransport> start() {
        return null;
    }

    public void bind(DisposableServer server) {
        this.server = server;
    }

    @Override
    public Mono<Void> dispose() {
        return Mono.fromRunnable(() -> {
            server.dispose();
        });
    }

    @Override
    public Mono<ReactiveTransport> bind() {
        throw new UnsupportedOperationException("Unimplemented method 'bind'");
    }

    public abstract  Mono<DisposableServer> careatDisposableServer();
}
