package io.cyberflux.engine.spring.web;

import org.springframework.web.bind.annotation.GetMapping;

import io.cyberflux.engine.spring.web.annotation.EngineController;
import reactor.core.publisher.Flux;

@EngineController("engine/api/reactor")
public class EngineReactorController {
    public EngineReactorController() {

    }
    @GetMapping
    public Flux<Object> get() {
        return Flux.empty();
    }
}
