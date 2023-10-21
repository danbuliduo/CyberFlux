package io.cyberflux.gateway.app.security;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.cyberflux.gateway.app.config.GatewaySecurtiyConfiguration;
import io.cyberflux.utils.io.JsonLoaderUtils;
import io.cyberflux.meta.ResponseCode;
import io.cyberflux.meta.ResponseResult;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityTokenFilter implements WebFilter {

	private static final Logger log = LoggerFactory.getLogger(SecurityTokenFilter.class);
	private final SecurityTokenProvider tokenProvider;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getPath().value();
		for(String PATH : GatewaySecurtiyConfiguration.VIR_PERMITTED_PATHS) {
			if(path.contains(PATH.replace("*", ""))) return chain.filter(exchange);
		}
		for(String PATH : GatewaySecurtiyConfiguration.FIX_PERMITTED_PATHS) {
			if(path.equals(PATH)) return chain.filter(exchange);
		}
		String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if(Objects.isNull(authorization)) {
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
			String data = JsonLoaderUtils.toJsonString(ResponseResult.codeResult(ResponseCode.REFU));
			DataBuffer body = response.bufferFactory().wrap(Objects.requireNonNull(data).getBytes(StandardCharsets.UTF_8));
			return response.writeWith(Mono.just(body));
		}
		try {
			tokenProvider.verifyToken(authorization).getSubject();
		} catch (Exception e) {
			log.error(e.getMessage());
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
			String data = JsonLoaderUtils.toJsonString(ResponseResult.codeResult(ResponseCode.REFU));
			DataBuffer body = response.bufferFactory().wrap(Objects.requireNonNull(data).getBytes(StandardCharsets.UTF_8));
			return response.writeWith(Mono.just(body));
		}
		exchange.getAttributes().put("token", authorization);
		return chain.filter(exchange);
	}
}
