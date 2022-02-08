package ru.tishin.springweb.gateway;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /*
    Даём возможность использовать класс JwtAuthFilter в yaml файле
     */
    public JwtAuthFilter() {
        super(Config.class);
    }

    /*
    Когда запрос проходит через JwtAuthFilter выполняется этот метод.
    Если в запросе есть валидный токен, мы добавляем хедер с именем пользователя в запрос.
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!isAuthMissing(request)) {
                final String token = getAuthHeader(request);
                if (jwtTokenUtil.isInvalid(token)) {
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }
                populateRequestWithHeaders(exchange, token);
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    /*
    Берем хедер авторизации из запроса, из него получаем массив и у первого элемента отпиливаем "Bearer "
    Т.е. получаем тело токена.
     */
    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    /*
    Проверяем отсутствует ли хедер авторизации и содержимое токена.
     */
    private boolean isAuthMissing(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            return true;
        }
        if (!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ")) {
            return true;
        }
        return false;
    }

    /*
    Парсим токен, получаем клэймсы. Подшиваем в запросы хедер Username имя пользователя из клэймсов (payload)
     */
    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate().header("username", claims.getSubject()).build();
    }

    public static class Config {
    }
}
