package io.cyberflux.cloud.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(("node"))
public class NodeEngineController {

	@GetMapping("/register")
	public Mono<String> register() {
		return Mono.just("200");
	}
}
