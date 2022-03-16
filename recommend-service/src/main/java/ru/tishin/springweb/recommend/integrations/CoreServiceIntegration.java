package ru.tishin.springweb.recommend.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tishin.springweb.api.core.OrderItemDto;
import ru.tishin.springweb.api.core.ProductDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CoreServiceIntegration {

    private final WebClient coreServiceWebClient;

    public List<OrderItemDto> getMostOrderedItems(int monthAgo) {
        return coreServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/order-items")
                        .queryParam("monthAgo", monthAgo)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OrderItemDto>>() {
                })
                .block();
    }

    public List<ProductDto> getAllProducts() {
        return coreServiceWebClient.get()
                .uri("/products/all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductDto>>() {
                })
                .block();
    }


}
