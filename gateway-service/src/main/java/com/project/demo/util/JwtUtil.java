package com.project.demo.util;

import com.project.demo.exception.JwtTokenMalformedException;
import com.project.demo.exception.JwtTokenMissingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtUtil {

	@Value("${jwtSecret}")
	private String jwtSecret;

	public Claims getClaims(final String token) {
		System.out.println("claimsToken");
		try {
			Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			return body;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}

	public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
		System.out.println("validateToken" + token);
		try {
			System.out.println("validateToken1" + token);
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

		} catch (SignatureException ex) {
			System.out.println("validateToken2" + token);
			throw new JwtTokenMalformedException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			System.out.println("validateToken3" + token);
			throw new JwtTokenMalformedException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			System.out.println("validateToken4" + token);
			throw new JwtTokenMalformedException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			System.out.println("validateToken5" + token);
			throw new JwtTokenMalformedException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			System.out.println("validateToken6" + token);
			throw new JwtTokenMissingException("JWT claims string is empty.");
		} catch (Exception e){
			System.out.println("validateToken7" + token);
			throw new JwtTokenMissingException(e.getMessage());
		}
		System.out.println("gakdapettoken");
	}
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}
}
