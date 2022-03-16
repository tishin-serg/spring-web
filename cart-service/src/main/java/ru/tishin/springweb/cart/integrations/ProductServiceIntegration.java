package ru.tishin.springweb.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.api.dto.ProductServiceAppError;
import ru.tishin.springweb.api.exceptions.IntegrationException;
import ru.tishin.springweb.api.exceptions.ProductServiceIntegrationException;

import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceClient;

    public ProductDto findById(Long productId) {
        return productServiceClient.get()
                .uri("/products/{id}", productId)// http://localhost:8189/web-market-core/api/v1/products/{id}
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(ProductServiceAppError.class).map(
                                body -> {
                                    if (body.getStatusCode().equals(ProductServiceAppError.ProductServiceErrors.PRODUCT_NOT_FOUND.name())) {
                                        return new ProductServiceIntegrationException("Ошибка интеграции с core-service: продукт #" +
                                                productId + " не найден");
                                    }
                                    return new ProductServiceIntegrationException("Ошибка интеграции с core-service: " +
                                            "Причина неизвестна");
                                }
                        )
                )
                .bodyToMono(ProductDto.class)
                .onErrorMap(Predicate.not(ProductServiceIntegrationException.class::isInstance),
                        throwable -> new IntegrationException("Product service integration don't response. " + throwable.getMessage()))
                .block();
    }

    public List<ProductDto> getAllProducts() {
        return productServiceClient.get()
                .uri("/products/all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductDto>>() {
                })
                .block();
    }

    // старая версия обработки исключений
    //    public ProductDto findById(Long productId) {
//        return productServiceClient.get()
//                .uri("/products/{id}", productId)// http://localhost:8189/web-market-core/api/v1/products/{id}
//                .retrieve()
//                .onStatus(HttpStatus.NOT_FOUND::equals,
//                        clientResponse -> clientResponse.bodyToMono(String.class).map(ResourceNotFoundException::new))
//                .bodyToMono(ProductDto.class)
//                .onErrorMap(Predicate.not(ResourceNotFoundException.class::isInstance),
//                        throwable -> new IntegrationException("Product service integration don't response. " + throwable.getMessage()))
//                .block();
//    }
}
