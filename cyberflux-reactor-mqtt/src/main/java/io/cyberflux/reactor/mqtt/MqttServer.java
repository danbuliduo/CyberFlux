package io.cyberflux.reactor.mqtt;

import io.cyberflux.meta.reactor.core.ReactiveServer;
import reactor.core.publisher.Mono;

public class MqttServer implements ReactiveServer {
    @Override
    public Mono<ReactiveServer> start() {
       return null;
    }
    @Override
    public ReactiveServer startAwait() {
        return start().block();
    }
    @Override
    public void shutdown() {

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
