package io.cyberflux.cloud.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cyberflux.cloud.core.model.UserAccount;
import io.cyberflux.cloud.core.repository.UserAccountRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("account")
public class UserAccountController {
	@Autowired
	UserAccountRepository repository;

	@GetMapping("/login")
	public Mono<String> login() {
		return Mono.just("fhf");
	}

	@PostMapping("/logout")
	public Mono<Void> logout() {
		return Mono.empty();
	}

	@GetMapping("/q/all")
	public Mono<UserAccount> q_all() {
		System.out.println(repository.existsById("Master").block());
		return repository.findById("Master");
	}
}
