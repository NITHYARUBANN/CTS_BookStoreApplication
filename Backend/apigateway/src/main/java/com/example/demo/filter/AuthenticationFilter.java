package com.example.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.example.demo.util.JwtUtil;
import com.google.common.net.HttpHeaders;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private JwtUtil util;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	public static class Config {
	}

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			if (validator.isSecured.test(exchange.getRequest())) {
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					logger.warn("Missing authorization header");
					return handleUnauthorized(exchange.getResponse(), "Missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}

				try {
					String role = util.extractRolesFromToken(authHeader);
					String requestedPath = exchange.getRequest().getPath().toString();
					String method = exchange.getRequest().getMethod().name();

					if (!isAuthorized(role, requestedPath, method)) {
						logger.warn("Unauthorized access for role: {} on path: {}", role, requestedPath);
						return handleUnauthorized(exchange.getResponse(), "Only admin can access this feature.");
					}

				} catch (Exception e) {
					logger.error("Invalid token", e);
					return handleUnauthorized(exchange.getResponse(), "Invalid token");
				}
			}
			return chain.filter(exchange);
		};
	}

	private boolean isAuthorized(String role, String path, String method) {
		if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
			return true; // Admin can access all paths
		} else if ("ROLE_CUSTOMER".equalsIgnoreCase(role)) {
			if (path.startsWith("/books")) {
				return path.equals("/books/getAll") || path.matches("/books/\\d+") || path.equals("/books/search")
						|| path.equals("/books/searchByAuthor") || path.equals("/books/searchByTitle");
			} else if (path.startsWith("/carts")) {
				return !path.equals("/carts/allOrders");
			}
		}
		return false;
	}

	private Mono<Void> handleUnauthorized(ServerHttpResponse response, String message) {
		response.setStatusCode(HttpStatus.FORBIDDEN);
		String json = String.format("{\"message\": \"%s\"}", message);
		DataBuffer buffer = response.bufferFactory().wrap(json.getBytes());
		return response.writeWith(Mono.just(buffer));
	}
}