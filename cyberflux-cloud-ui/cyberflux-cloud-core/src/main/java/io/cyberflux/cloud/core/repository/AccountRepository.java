package io.cyberflux.cloud.core.repository;


import org.springframework.data.repository.CrudRepository;

import io.cyberflux.cloud.core.model.AccountEntity;

public interface AccountRepository extends CrudRepository<AccountEntity, String> {

}
