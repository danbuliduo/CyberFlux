package io.cyberflux.cloud.core.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import io.cyberflux.cloud.core.model.UserAccountModel;

public interface UserAccountRepository extends ReactiveCrudRepository<UserAccountModel, String> {

}
