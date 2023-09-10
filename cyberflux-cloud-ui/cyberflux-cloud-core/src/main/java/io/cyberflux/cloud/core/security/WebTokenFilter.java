package io.cyberflux.cloud.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class WebTokenFilter implements WebFilter {

	@Autowired
	private WebTokenProvider provider;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		System.out.println("WebTokenFilter::filter");
		ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
		String path = request.getPath().value();
		if (path.contains("/api/account/login")) {
			return chain.filter(exchange);
		}
		String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		exchange.getAttributes().put("token", authorization);
		System.out.println("authorization: " + authorization);
		return chain.filter(exchange);

	}
}
