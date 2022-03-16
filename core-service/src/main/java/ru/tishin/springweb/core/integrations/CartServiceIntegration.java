package ru.tishin.springweb.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tishin.springweb.api.cart.CartDto;
import ru.tishin.springweb.api.dto.CartServiceAppError;
import ru.tishin.springweb.api.exceptions.CartServiceIntegrationException;
import ru.tishin.springweb.core.properties.CartServiceIntegrationProperties;

@RequiredArgsConstructor
@Component
@EnableConfigurationProperties(CartServiceIntegrationProperties.class)

public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public CartDto getCurrentCart(String username) {
        return cartServiceWebClient.get()
                .uri("/0") // http://localhost:8187/web-market-cart/api/v1/carts/0
                .header("username", username)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(CartServiceAppError.class).map(
                                body -> {
                                    if (body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_NOT_FOUND.name())) {
                                        return new CartServiceIntegrationException("Ошибка интеграции с cart-service: Корзина не " +
                                                "найдена");
                                    }
                                    if (body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_SERVICE_REDIS_CONNECTION_ERROR.name())) {
                                        return new CartServiceIntegrationException("Ошибка интеграции с cart-service: " +
                                                "Соединение с редисом не потеряно");
                                    }
                                    return new CartServiceIntegrationException("Ошибка интеграции с cart-service: " +
                                            "Причина неизвестна");
                                }
                        )
                )
                .bodyToMono(CartDto.class)
                .block();
    }

}
