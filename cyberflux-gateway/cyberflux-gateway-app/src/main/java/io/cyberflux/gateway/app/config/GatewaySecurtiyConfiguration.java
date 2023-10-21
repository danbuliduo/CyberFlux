package io.cyberflux.gateway.app.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import io.cyberflux.gateway.app.security.SecurityContextRepository;
import io.cyberflux.gateway.app.security.SecurityTokenFilter;
import lombok.RequiredArgsConstructor;

@SpringBootConfiguration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class GatewaySecurtiyConfiguration {

	private final SecurityContextRepository securityContextRepository;

	public static final String[] FIX_PERMITTED_PATHS = {
		"/gateway/api/account/login",
		"/gateway/api/engine/register",
	};

	public static final String[] VIR_PERMITTED_PATHS = {
		"/gateway/api/engine/**",
		"/gateway/api/channel/**",
		"/gateway/api/reactor/**",
		"/gateway/api/actuator/**",
		"/gateway/api/subscription/**",
		"/gateway/api/engine",
		"/gateway/api/channel",
		"/gateway/api/reactor",
		"/gateway/api/actuator",
		"/gateway/api/subscription",
		"/engine/api/**",
		"/gateway/sse",
		"/engine/sse",
		"/subscribe/actuator"
	};



	@Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, SecurityTokenFilter filter) {
		http.authorizeExchange(exchanges ->
			exchanges.pathMatchers(FIX_PERMITTED_PATHS).permitAll()
			    .pathMatchers(VIR_PERMITTED_PATHS).permitAll()
				.anyExchange().authenticated()
		).addFilterAfter(filter, SecurityWebFiltersOrder.FIRST)
		.securityContextRepository(securityContextRepository)
		.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
		.logout(ServerHttpSecurity.LogoutSpec::disable)
		.csrf(ServerHttpSecurity.CsrfSpec::disable)
		.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
		return http.build();
    }
}

