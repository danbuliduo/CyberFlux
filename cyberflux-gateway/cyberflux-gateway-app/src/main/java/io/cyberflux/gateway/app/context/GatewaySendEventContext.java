package io.cyberflux.gateway.app.context;

import org.springframework.http.codec.ServerSentEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public final class GatewaySendEventContext {
    private final Sinks.Many<ServerSentEvent<?>> sinks;

    private GatewaySendEventContext() {
        this.sinks = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Flux<ServerSentEvent<?>> events() {
        return sinks.asFlux();
    }

    public void emit(ServerSentEvent<?> event) {
        sinks.tryEmitNext(event);
    }

    public static GatewaySendEventContext instance() {
        return GatewaySendEventContext.Instance.INSTANCE;
    }

    public static class Instance {
        private static final GatewaySendEventContext INSTANCE = new GatewaySendEventContext();
    }

}
