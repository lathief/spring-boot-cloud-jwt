package com.project.demo.filter;

import java.util.List;
import java.util.function.Predicate;

import com.project.demo.exception.JwtTokenMalformedException;
import com.project.demo.exception.JwtTokenMissingException;
import com.project.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

		final List<String> apiEndpoints = List.of("/signup", "/signin");
		System.out.println("oke1");
		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));
		System.out.println("oke2");
		if (isApiSecured.test(request)) {
			if (!request.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				System.out.println("oke3");
				return response.setComplete();
			}
			System.out.println("oke4");
			String tmptoken = request.getHeaders().getOrEmpty("Authorization").get(0);
			final String token = tmptoken.substring(7);
			try {
				jwtUtil.validateToken(token);
				System.out.println("oke5");
			} catch (JwtTokenMalformedException | JwtTokenMissingException e) {
				// e.printStackTrace();
				System.out.println("oke6");
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.BAD_REQUEST);

				return response.setComplete();
			}
			System.out.println("oke7");
			Claims claims = jwtUtil.getClaims(token);
			exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
		}
		System.out.println("okelast");
		return chain.filter(exchange);
	}

}
