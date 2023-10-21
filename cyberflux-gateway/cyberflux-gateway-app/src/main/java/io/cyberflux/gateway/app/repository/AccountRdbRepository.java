package io.cyberflux.gateway.app.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import io.cyberflux.gateway.app.codec.AccountEntity;
import reactor.core.publisher.Mono;

public interface AccountRdbRepository extends R2dbcRepository<AccountEntity, String> {

	@Modifying
	@Query("INSERT INTO account VALUES(:username, :password, :remarks, :permissions)")
	Mono<AccountEntity> save(String username, String password, String remarks, String[] permissions);

	@Query("SELECT * FROM account WHERE username=:username AND password=:password")
	Mono<AccountEntity> findByUsernameAndPassword(String username, String password);
	@Modifying
	@Query("UPDATE account SET password=:password WHERE username=:username")
	Mono<Integer> updatePasswordByUsername(String username, String password);
}
