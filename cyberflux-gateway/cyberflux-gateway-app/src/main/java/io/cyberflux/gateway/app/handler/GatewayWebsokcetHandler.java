package io.cyberflux.gateway.app.handler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import io.cyberflux.engine.spring.service.EngineActuatorService;
import io.cyberflux.gateway.app.annotation.WebSocketController;
import io.cyberflux.gateway.app.annotation.WebSocketMapping;
import io.cyberflux.utils.io.JsonLoaderUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebSocketController
@WebSocketMapping("/")
public class GatewayWebsokcetHandler implements WebSocketHandler {

    private final Map<String, WebSocketSession> sessions;

    GatewayWebsokcetHandler(EngineActuatorService subscriber) {
        this.sessions = new ConcurrentHashMap<>();
        subscriber.measurementFlux().subscribe(item -> {
            Flux.fromIterable(sessions.values())
                .subscribe(session -> {
                    if(session.isOpen()) {
                        session.send(Mono.just(
                            session.textMessage(Objects.requireNonNull(JsonLoaderUtils.toJsonString(item)))
                        )).subscribe();
                    } else {
                        sessions.remove(session.getId());
                    }
                });
        });
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        System.out.println(session.getId());
        this.sessions.put(session.getId(), session);
        return session.receive().then();
    }

    public List<WebSocketSession> findAllSessions() {
        return new ArrayList<>(sessions.values());
    }

    public Optional<WebSocketSession> findSesssionByToken(String id) {
        return Optional.of(sessions.get(id));
    }
}
