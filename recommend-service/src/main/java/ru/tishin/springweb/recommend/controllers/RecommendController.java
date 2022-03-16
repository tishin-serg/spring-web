package ru.tishin.springweb.recommend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.api.cart.ProductsWithDailyStatDto;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.recommend.services.RecommendService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommends")
@RequiredArgsConstructor
@Tag(name = "Контроллер сервиса рекомендаций", description = "Отвечает за рекомендации по продуктам для пользователей")
public class RecommendController {
    private final RecommendService recommendService;

    @GetMapping("/most-ordered-products")
    @Operation(summary = "Отдает самые продаваемые товары за @monthAgo месяцев назад от сегодняшнего дня")
    public List<ProductDto> getMostOrderedProductsFor(
            @RequestParam(required = false, defaultValue = "5") @Parameter(name = "Лимит товаров") int limit,
            @RequestParam(required = false, defaultValue = "1") @Parameter(name = "Месяцев назад") int monthAgo) {
        return recommendService.getMostOrderedProducts(limit, monthAgo);
    }

    @GetMapping("/most-added-to-cart-products")
    @Operation(summary = "Возвращает самые добавляемые товары в корзину за последние сутки")
    public List<ProductDto> getMostAddedToCartProducts(@RequestParam(required = false, defaultValue = "5") int limit) {
        return recommendService.getMostAddedInCartProducts(limit);
    }

    @GetMapping("/carts-stat")
    @Operation(
            summary = "Возвращает статистику по добавляемым в корзину товарам за определенный день",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductsWithDailyStatDto.class))
                    )
            })
    @ResponseBody
    public ProductsWithDailyStatDto getCartStatisticsForDefinedDay(@RequestParam int year,
                                                                   @RequestParam int month,
                                                                   @RequestParam int day) {
        return recommendService.getStatistics(year, month, day);
    }
}
