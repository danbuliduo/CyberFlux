package io.cyberflux.cloud.core.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import io.cyberflux.cloud.core.model.UserAccount;


public interface UserAccountRepository extends ReactiveCrudRepository<UserAccount, String>{

}
