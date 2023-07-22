package io.cyberflux.cloud.core.service;

import io.cyberflux.cloud.core.model.UserAccountModel;
import reactor.core.publisher.Mono;

public interface UserAccountService {
	Mono<UserAccountModel> login(UserAccountModel model);
}
