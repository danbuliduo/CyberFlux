package io.cyberflux.gateway.app.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import io.cyberflux.gateway.app.context.GatewaySecurityProperties;
import io.cyberflux.gateway.app.codec.AccountEntity;
import io.cyberflux.meta.ResponseCode;
import io.cyberflux.meta.ResponseResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityTokenProvider {

	private final GatewaySecurityProperties properties;

	public Duration expires() {
		return properties.getJwt().getExpires();
	}

	public String prefix() {
		return properties.getJwt().getPrefix();
	}

	public String secretKey() {
        return properties.getJwt().getSecretKey();
	}

	public ResponseResult createResult(AccountEntity account) {
		ResponseResult result = new ResponseResult();
		result.setCode(ResponseCode.ACCE);
		result.setHeader(new HashMap<>(2){{
			put("token", createToken(account.getUsername()));
			put("expires", expires().toMillis());
		}});
		result.setPayload(account);
		return result;
	}

	public String createToken(String username) {
		Instant instant = Instant.now();
		return Jwts.builder().setSubject(username)
			.signWith(SignatureAlgorithm.HS256, secretKey())
			.setExpiration(Date.from(instant.plus(expires())))
			.setIssuedAt(Date.from(instant))
			.compact();
	}

	public Claims verifyToken(String token) {
		return Jwts.parser().setSigningKey(secretKey()).parseClaimsJws(token).getBody();
	}
}
