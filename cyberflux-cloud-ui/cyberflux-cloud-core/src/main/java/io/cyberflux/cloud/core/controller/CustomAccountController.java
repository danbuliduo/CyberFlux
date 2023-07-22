package io.cyberflux.cloud.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cyberflux.cloud.core.model.UserAccountModel;
import io.cyberflux.cloud.core.service.UserAccountService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("account")
public class CustomAccountController {

	@Autowired
	UserAccountService accountService;

	@PostMapping("/login")
	public Mono<UserAccountModel> login(@RequestBody UserAccountModel model) {
		return accountService.login(model);
	}

	@PostMapping("/logout")
	public Mono<Void> logout() {
		return Mono.empty();
	}

	@GetMapping("/query/all")
	public Flux<UserAccountModel> query_all() {
		return Flux.empty();
	}
}
