package io.cyberflux.cloud.core.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import io.cyberflux.cloud.core.model.AdminAccountModel;

public interface AdminAccountRepository extends ReactiveCrudRepository<AdminAccountModel, String> {

}
