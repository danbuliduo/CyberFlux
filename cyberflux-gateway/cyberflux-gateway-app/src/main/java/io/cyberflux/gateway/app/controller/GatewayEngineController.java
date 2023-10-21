package io.cyberflux.gateway.app.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cyberflux.engine.core.model.EngineRegisterMessage;
import io.cyberflux.gateway.app.codec.EngineEntity;
import io.cyberflux.gateway.app.service.GatewayEngineService;
import io.cyberflux.meta.ResponseResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("gateway/api/engine")
public class GatewayEngineController {
;

	private final GatewayEngineService service;

	public GatewayEngineController(GatewayEngineService service) {
		this.service = service;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<EngineEntity> get() {
		return service.get();
	}

	@PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseResult> register(@RequestBody EngineRegisterMessage message) {
		return service.register(message);
	}
}
