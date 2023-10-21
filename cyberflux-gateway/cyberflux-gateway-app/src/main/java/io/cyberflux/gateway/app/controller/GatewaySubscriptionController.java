package io.cyberflux.gateway.app.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cyberflux.gateway.app.service.GatewaySubscriptionService;
import io.netty.handler.codec.http.HttpMethod;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("gateway/api/subscription")
public class GatewaySubscriptionController {

    private final String INFIX1 = "/subscription";
    private final String INFIX2 = "/subscription/count";

    private final  GatewaySubscriptionService service;

	public GatewaySubscriptionController(GatewaySubscriptionService service) {
		this.service = service;
	}

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<?> get() {
        return service.request(HttpMethod.GET, INFIX1);
    }

    @GetMapping(path = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<?> getCount() {
        return service.request(HttpMethod.GET, INFIX2);
    }
}
