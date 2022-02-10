package ru.tishin.springweb.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tishin.springweb.api.core.ProductDto;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceClient;

    public ProductDto findById(Long productId) {
        return productServiceClient.get()
                .uri("/products/{id}", productId) // http://localhost:8189/web-market-core/api/v1/products/{id}
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }
}
