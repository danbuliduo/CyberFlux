package io.cyberflux.cloud.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cyberflux.cloud.core.ServiceCode;
import io.cyberflux.cloud.core.model.AccountEntity;
import io.cyberflux.cloud.core.repository.AccountRepository;
import io.cyberflux.cloud.core.security.WebTokenProvider;
import io.cyberflux.cloud.core.service.AccountService;
import io.cyberflux.cloud.core.utils.HttpResult;
import io.cyberflux.common.utils.CyberJsonUtils;
import io.cyberflux.common.utils.CyberNanoIdUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
	private AccountRepository repository;

	@Autowired
	private WebTokenProvider provider;

	@Override
	public Flux<AccountEntity> findAll(String token) {
		return Flux.fromIterable(repository.findAll());
	}

	@Override
	public Mono<String> login(AccountEntity account) {
		return Mono.just(repository.findById(account.getUsername())).map(item -> {
			HttpResult result = new HttpResult();
			if (item.isPresent()) {
				String token = provider.createToken(account);
				System.out.println("login: "+token);
				result.setCode(ServiceCode.SUCCEED);
				result.setPayload(new HashMap<>(){{
					put("token", token);
				}});
				return CyberJsonUtils.toJsonString(result);
			}
			result.setCode(ServiceCode.FAILED);
			return CyberJsonUtils.toJsonString(result);
		});
	}

	@Override
	public Mono<Void> logout(String token) {
		return Mono.empty();
	}
}
