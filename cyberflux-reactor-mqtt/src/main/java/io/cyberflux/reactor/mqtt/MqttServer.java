package io.cyberflux.reactor.mqtt;

import java.util.ArrayList;
import java.util.List;

import io.cyberflux.meta.reactor.core.ReactiveServer;
import io.cyberflux.reactor.mqtt.transport.MqttTransport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MqttServer implements ReactiveServer {
    private final List<MqttTransport> transports = new ArrayList<>();


    @Override
    public Mono<ReactiveServer> start() {
       return Flux.fromIterable(transports).collectList().thenReturn(this);
    }

    @Override
    public ReactiveServer startAwait() {
        return start().block();
    }

    @Override
    public void shutdown() {
        transports.forEach(MqttTransport::dispose);
    }

    public static MqttServer.MqttServerBuilder builder() {
        return new MqttServer.MqttServerBuilder();
    }

    public static class MqttServerBuilder {
        public MqttServer build() {
            return new MqttServer();
        }
    }
}
