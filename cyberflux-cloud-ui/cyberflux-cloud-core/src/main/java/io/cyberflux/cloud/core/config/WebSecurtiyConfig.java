package io.cyberflux.cloud.core.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import io.cyberflux.cloud.core.security.SecurityContextRepository;
import io.cyberflux.cloud.core.security.WebTokenFilter;


@SpringBootConfiguration
@EnableWebFluxSecurity
public class WebSecurtiyConfig {

	@Autowired
	private SecurityContextRepository securityContextRepository;

	@Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, WebTokenFilter filter) {
		System.out.println("WebSecurtiyConfig::securityWebFilterChain");
        http.authorizeExchange(exchanges -> exchanges
            .pathMatchers("/api/account/login").permitAll()
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

