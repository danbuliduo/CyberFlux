package io.cyberflux.engine.spring.web;

import io.cyberflux.engine.spring.service.EngineActuatorService;
import io.cyberflux.engine.spring.web.annotation.EngineController;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@EngineController("engine/sse")
public class EngineSendEventController {
    private final EngineActuatorService engineActuatorService;

    public EngineSendEventController(EngineActuatorService engineActuatorService) {
        this.engineActuatorService = engineActuatorService;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Object> sse() {
        return this.engineActuatorService.measurementFlux().map(item -> ServerSentEvent.builder(item).event("metric-update").build());
    }
}
