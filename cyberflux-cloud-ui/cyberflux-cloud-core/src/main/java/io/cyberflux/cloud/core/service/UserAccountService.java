package io.cyberflux.cloud.core.service;

import io.cyberflux.cloud.core.model.UserAccount;
import reactor.core.publisher.Mono;

public interface UserAccountService {
	Mono<UserAccount> loginById();
}
