package io.cyberflux.gateway.app.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cyberflux.gateway.app.service.GatewayActuatorService;
import io.netty.handler.codec.http.HttpMethod;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("gateway/api/actuator")
public class GatewayActuatorController {

    private final String INFIX1 = "/actuator";

    private final GatewayActuatorService service;

    public GatewayActuatorController(GatewayActuatorService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<?> get() {
        return service.request(HttpMethod.GET, INFIX1);
    }
}
