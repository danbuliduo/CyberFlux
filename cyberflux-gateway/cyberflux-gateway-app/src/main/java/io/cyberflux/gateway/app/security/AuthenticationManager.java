package io.cyberflux.gateway.app.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationManager.class);

	private final SecurityTokenProvider provider;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return Mono.just(authentication).map(item -> {
			return provider.verifyToken(item.getCredentials().toString());
		}).onErrorResume(error -> {
			log.error(error.getMessage());
			return Mono.empty();
		}).map(claims -> new UsernamePasswordAuthenticationToken(
			claims.getSubject(), null, null
		));
	}
}
