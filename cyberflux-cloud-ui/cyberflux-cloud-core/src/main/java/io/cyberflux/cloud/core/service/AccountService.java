package io.cyberflux.cloud.core.service;

import io.cyberflux.cloud.core.model.AccountEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
	Flux<AccountEntity> findAll(String token);
	Mono<String> login(AccountEntity account);
	Mono<Void> logout(String token);
}
