package ru.tishin.springweb.recommend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.api.cart.ProductsWithDailyStatDto;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.recommend.services.RecommendService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommends")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;

    /*
    Отдает самые продаваемые товары за @monthAgo месяцев назад от сегодняшнего дня
     */
    @GetMapping("/most-ordered-products")
    public List<ProductDto> getMostOrderedProductsFor(@RequestParam(required = false, defaultValue = "5") int limit,
                                                      @RequestParam(required = false, defaultValue = "1") int monthAgo) {
        return recommendService.getMostOrderedProducts(limit, monthAgo);
    }

    /*
    Возвращает самые добавляемые товары в корзину за последние сутки
     */
    @GetMapping("/most-added-to-cart-products")
    public List<ProductDto> getMostAddedToCartProducts(@RequestParam(required = false, defaultValue = "5") int limit) {
        return recommendService.getMostAddedInCartProducts(limit);
    }

    /*
    Возвращает статистику по добавляемым в корзину товарам за определенный день
     */
    @GetMapping("/carts-stat")
    @ResponseBody
    public ProductsWithDailyStatDto getCartStatisticsForDefinedDay(@RequestParam int year,
                                                                   @RequestParam int month,
                                                                   @RequestParam int day) {
        return recommendService.getStatistics(year, month, day);
    }
}
