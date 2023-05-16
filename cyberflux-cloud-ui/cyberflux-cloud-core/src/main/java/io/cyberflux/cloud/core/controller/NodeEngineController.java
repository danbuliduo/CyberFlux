package io.cyberflux.cloud.core.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cyberflux.cloud.core.service.NodeEngineService;
import io.cyberflux.meta.models.node.NodeEngineModel;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("node")
public class NodeEngineController {
	@Autowired
	NodeEngineService nodeEngineService;

	@GetMapping("/query/all")
	public Mono<Map<String, Set<NodeEngineModel>>> query_all() {
		return Mono.just(nodeEngineService.findAll());
	}

	@PostMapping("/register")
	public Mono<String> register(@RequestBody NodeEngineModel model) {
		nodeEngineService.register(model);
		return Mono.just("200");
	}
}
