package io.cyberflux.examples.node1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class TestController {
	@GetMapping("/test")
	public Mono<String> t() {
		return Mono.just("dgdg");
	}
}
