package io.cyberflux.engine.spring.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import io.cyberflux.engine.core.Engine;
import io.cyberflux.engine.spring.web.annotation.EngineController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@EngineController("engine/api/channel")
public class EngineChannelController {

    public EngineChannelController() {

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Tuple2<Object, Object>> get() {
        return Flux.zip(
            Flux.fromArray(Engine.CHANNEL_CONTEXT_OPTIONS)
                .map(item -> item.channelGroup().type()),
            Flux.fromArray(Engine.CHANNEL_CONTEXT_OPTIONS)
                .map(item -> item.channelGroup().findAll())
        );
    }
}
