package io.cyberflux.cloud.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

	@Autowired
	private WebTokenProvider provider;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		System.out.println("WebAuthManager::authenticate");
		System.out.println(authentication.getCredentials());
		return Mono.just(authentication).map(auth -> {
			return provider.verifyToken(auth.getCredentials().toString());
		}).onErrorResume(err -> {
			System.out.println("ERR:"+err);
			return Mono.empty();
		}).map(claims -> new UsernamePasswordAuthenticationToken(
			claims.getSubject(), null, null
		));
	}
}
