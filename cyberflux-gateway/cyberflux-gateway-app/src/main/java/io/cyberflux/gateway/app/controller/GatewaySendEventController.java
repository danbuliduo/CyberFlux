package io.cyberflux.gateway.app.controller;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cyberflux.gateway.app.context.GatewaySendEventContext;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("gateway/sse")
public class GatewaySendEventController {

    private final GatewaySendEventContext sendEventContext;

    public GatewaySendEventController() {
        sendEventContext = GatewaySendEventContext.instance();
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<?>> events() {
        return sendEventContext.events();
    }
}
