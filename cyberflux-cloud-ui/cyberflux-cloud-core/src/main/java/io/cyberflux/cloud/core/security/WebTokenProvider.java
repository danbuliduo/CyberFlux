package io.cyberflux.cloud.core.security;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import io.cyberflux.cloud.core.model.AccountEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class WebTokenProvider {
	@Value("${spring.jwt.secret-key}")
	private String SECRET_KEY;
	@Value("${spring.jwt.expires}")
	private Long EXPIRES;

	public String createToken(AccountEntity account) {
		Instant instant = Instant.now();
		return Jwts.builder().setSubject(account.getUsername())
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.setExpiration(Date.from(instant.plus(Duration.ofMillis(EXPIRES))))
			.setIssuedAt(Date.from(instant))
			.compact();
	}

	public Claims verifyToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

}
