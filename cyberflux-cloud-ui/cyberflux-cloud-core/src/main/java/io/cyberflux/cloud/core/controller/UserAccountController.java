package io.cyberflux.cloud.core.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("account")
public class UserAccountController {
	@PostMapping("/login")
	Mono<String> login() {
		return Mono.just("fhf");
	}

	@PostMapping("/logout")
	Mono<Void> logout() {
		return Mono.empty();
	}

}
