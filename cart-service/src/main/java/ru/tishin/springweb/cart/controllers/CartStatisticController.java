package ru.tishin.springweb.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.api.cart.ProductsWithDailyStatDto;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.cart.converters.CartConverter;
import ru.tishin.springweb.cart.services.CartStatisticService;

import java.util.List;

/*
    Контроллер даёт доступ к статистике по совершаемым действиям в корзине
 */

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class CartStatisticController {
    private final CartStatisticService cartStatisticService;
    private final CartConverter cartConverter;

    /*
    Отдает статистику по определенным товарам (id которых указаны в @RequestBody) за последний день
     */
    @PostMapping
    public List<ProductDto> getStatistics(@RequestBody List<Long> productIdList) {
        return cartStatisticService.getStatisticsFromProducts(productIdList);
    }

    /*
    Отдаёт статистику по всем товарам добавленным в корзину за определенный день
     */
    @GetMapping
    public ProductsWithDailyStatDto getStatisticsForDefinedDay(@RequestParam Integer year,
                                                               @RequestParam Integer month,
                                                               @RequestParam Integer day) {
        return cartConverter.entityToDto(cartStatisticService.getStatisticsForDefinedPeriod(year, month, day));
    }

    /*
    Принудительно сохраняет ежедневную статистику в redis и чистит кэш. Для тестов
     */
    @GetMapping("/save")
    public void saveStat() {
        cartStatisticService.saveDailyCartStatistic();
    }
}
