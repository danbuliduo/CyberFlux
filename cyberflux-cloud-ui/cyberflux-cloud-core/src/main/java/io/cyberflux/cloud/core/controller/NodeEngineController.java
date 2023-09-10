package io.cyberflux.cloud.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cyberflux.cloud.core.service.NodeEngineService;
import io.cyberflux.meta.models.node.NodeEngineModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/node/engine")
//@RefreshScope
public class NodeEngineController {
	@Autowired
	NodeEngineService service;

	@GetMapping("/query-all")
	public Flux<NodeEngineModel> queryAll() {
		return Flux.fromIterable(service.findAll());
	}

	@PostMapping("/register")
	public Mono<String> register(@RequestBody NodeEngineModel model) {
		// nodeEngineService.register(model);
		return Mono.just("200");
	}
}
