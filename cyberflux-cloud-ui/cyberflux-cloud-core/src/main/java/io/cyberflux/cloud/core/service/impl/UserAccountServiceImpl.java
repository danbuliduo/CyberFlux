package io.cyberflux.cloud.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cyberflux.cloud.core.model.UserAccountModel;
import io.cyberflux.cloud.core.repository.UserAccountRepository;
import io.cyberflux.cloud.core.service.UserAccountService;
import reactor.core.publisher.Mono;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired UserAccountRepository repository;

	@Override
	public Mono<UserAccountModel> login(UserAccountModel model) {
		return Mono.empty();
	}

}
