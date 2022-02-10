package ru.tishin.springweb.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tishin.springweb.api.cart.CartDto;

@RequiredArgsConstructor
@Component
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public CartDto getCurrentCart(String username) {
        return cartServiceWebClient.get()
                .uri("/0") // http://localhost:8187/web-market-cart/api/v1/carts/0
                .header("username", username)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }
}
