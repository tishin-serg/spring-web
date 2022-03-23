package ru.tishin.springweb.recommend.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tishin.springweb.api.cart.ProductsWithDailyStatDto;
import ru.tishin.springweb.api.core.ProductDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartStatisticServiceIntegration {

    private final WebClient cartStatisticServiceWebClient;

    public List<ProductDto> getStatistics(List<Long> productIdList) {
        return cartStatisticServiceWebClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productIdList))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductDto>>() {
                })
                .block();
    }

    /*
    Получаем статистику из cart-service за определенный день
     */
    public ProductsWithDailyStatDto getStatistics(Integer year, Integer month, Integer day) {
        return cartStatisticServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("year", year)
                        .queryParam("month", month)
                        .queryParam("day", day)
                        .build())
                .retrieve()
                .bodyToMono(ProductsWithDailyStatDto.class)
                .block();
    }
}
