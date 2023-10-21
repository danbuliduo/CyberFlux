package io.cyberflux.gateway.app.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import io.cyberflux.gateway.app.codec.AccountEntity;
import io.cyberflux.gateway.app.repository.AccountRdbRepository;
import io.cyberflux.gateway.app.security.SecurityTokenProvider;
import io.cyberflux.meta.ResponseCode;
import io.cyberflux.meta.ResponseResult;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("gateway/api/account")
@RequiredArgsConstructor
public class GatewayAccountController {

	private final AccountRdbRepository repository;
	private final SecurityTokenProvider provider;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseResult> post(@RequestBody AccountEntity account) {
		return repository.save(account.getUsername(), account.getPassword(), account.getRemarks(), account.getPermissions())
		    .thenReturn(ResponseResult.codeResult(ResponseCode.ACCE))
		    .onErrorResume(e -> Mono.empty());
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseResult> putByUsername(@RequestBody AccountEntity account) {
		return repository.updatePasswordByUsername(account.getUsername(), account.getPassword())
			.map(item -> provider.createResult(account))
			.onErrorResume(e -> Mono.empty());
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<AccountEntity> get() {
		return repository.findAll()
			.flatMap(item-> Mono.just(item.desensitization()))
			.onErrorResume(e -> Flux.empty());
	}

	@GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<AccountEntity> getByUsername(@PathVariable String username) {
		return repository.findById(username).onErrorResume(e -> Mono.empty());
	}

	@DeleteMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseResult> delByUsername(@PathVariable String username) {
		return repository.deleteById(username)
			.thenReturn(ResponseResult.codeResult(ResponseCode.ACCE))
		    .onErrorResume(e -> Mono.empty());
	}

	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseResult> login(@RequestBody AccountEntity account) {
		return repository.findByUsernameAndPassword(
			account.getUsername(), account.getPassword()
		).map(provider::createResult)
		.switchIfEmpty(
			Mono.just(ResponseResult.codeResult(ResponseCode.REFU))
		).onErrorResume(e -> Mono.empty());
	}

	@PostMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseResult> logout(ServerWebExchange exchange) {
		exchange.getAttributes().remove("token");
		return Mono.just(ResponseResult.codeResult(ResponseCode.ACCE));
	}

	@PostMapping(path = "/reverseauth", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseResult> reverseauth(ServerWebExchange exchange) {
		return Mono.just(exchange.getAttribute("token"))
			.cast(String.class)
			.map(item -> provider.verifyToken(item).getSubject())
			.flatMap(repository::findById)
			.map(item -> {
				ResponseResult result = new ResponseResult();
				result.setCode(ResponseCode.ACCE);
				result.setPayload(item.desensitization());
				return result;
			})
			.switchIfEmpty(Mono.just(ResponseResult.codeResult(ResponseCode.REFU)))
			.onErrorResume(e -> {
				System.out.println("########");
				System.out.println(e.getMessage());
			return Mono.just(ResponseResult.codeResult(ResponseCode.REFU));});
	}
}
