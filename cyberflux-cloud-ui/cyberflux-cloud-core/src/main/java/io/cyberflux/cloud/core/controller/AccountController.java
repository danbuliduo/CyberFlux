package io.cyberflux.cloud.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import io.cyberflux.cloud.core.service.AccountService;
import io.cyberflux.cloud.core.model.AccountEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/account/")
public class AccountController {

	@Autowired
	private AccountService service;

	@PostMapping("login")
	public Mono<String> login(@RequestBody AccountEntity entity) {
		System.out.println("#####");
		return service.login(entity);
	}

	@PostMapping("logout")
	public Mono<Void> logout(@RequestBody String token) {
		return service.logout(token);
	}

	@PostMapping("query-all")
	public Flux<AccountEntity> queryAll(@RequestBody String token) {
		return service.findAll(token);
	}
}
