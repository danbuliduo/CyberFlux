package io.cyberflux.reactor.mqtt;

import java.util.ArrayList;
import java.util.List;

import io.cyberflux.meta.reactor.ProtocolType;
import io.cyberflux.meta.reactor.ReactiveServer;
import io.cyberflux.meta.reactor.core.AbstractReactiveServer;
import io.cyberflux.reactor.mqtt.transport.MqttTransport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MqttReactor extends AbstractReactiveServer {
    private final List<MqttTransport> transports = new ArrayList<>();

    public MqttReactor() {
        super(ProtocolType.MQTT);
    }

    @Override
    public Mono<ReactiveServer> start() {
        System.out.println("sdf");
       return Flux.fromIterable(transports).collectList().thenReturn(this);
    }

    @Override
    public void shutdown() {
        transports.forEach(MqttTransport::dispose);
    }

    public static MqttReactor.MqttServerBuilder builder() {
        return new MqttReactor.MqttServerBuilder();
    }

    public static class MqttServerBuilder {
        public MqttReactor build() {
            return new MqttReactor();
        }
    }
}
