package ru.tishin.springweb.cart.controllers;

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
import ru.tishin.springweb.api.dto.ProductServiceAppError;
import ru.tishin.springweb.cart.converters.CartConverter;
import ru.tishin.springweb.cart.services.CartStatisticService;

import java.util.List;

/*
    Контроллер даёт доступ к статистике по совершаемым действиям в корзине
 */

@Tag(name = "Контроллер статистики корзин", description = "Даёт доступ к статистике по совершаемым действиям в корзине")
@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class CartStatisticController {
    private final CartStatisticService cartStatisticService;
    private final CartConverter cartConverter;

    @PostMapping
    @Operation(
            summary = "Отдает статистику по определенным товарам (id которых указаны в @RequestBody) за последний день",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            })
    public List<ProductDto> getStatistics(@RequestBody List<Long> productIdList) {
        return cartStatisticService.getStatisticsFromProducts(productIdList);
    }

    @GetMapping
    @Operation(
            summary = "Отдаёт статистику по всем товарам добавленным в корзину за определенный день",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductsWithDailyStatDto.class))
                    )
            })
    public ProductsWithDailyStatDto getStatisticsForDefinedDay(@RequestParam @Parameter(name = "Год") Integer year,
                                                               @RequestParam @Parameter(name = "Месяц") Integer month,
                                                               @RequestParam @Parameter(name = "День") Integer day) {
        return cartConverter.entityToDto(cartStatisticService.getStatisticsForDefinedPeriod(year, month, day));
    }

    @GetMapping("/save")
    @Operation(summary = "Принудительно сохраняет ежедневную статистику в redis и чистит кэш. Для тестов")
    public void saveStat() {
        cartStatisticService.saveDailyCartStatistic();
    }
}
