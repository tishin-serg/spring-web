package ru.tishin.springweb.recommend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.api.cart.ProductsWithDailyStatDto;
import ru.tishin.springweb.api.core.OrderItemDto;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.recommend.integrations.CartStatisticServiceIntegration;
import ru.tishin.springweb.recommend.integrations.CoreServiceIntegration;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecommendService {
    private final CoreServiceIntegration coreServiceIntegration;
    private final CartStatisticServiceIntegration cartStatisticService;

    /*
    Получаем с core-service @limit заказанных продуктов за @monthAgo месяцев,
    группируем и сортируем данные для фронта.
     */
    public List<ProductDto> getMostOrderedProducts(int limit, int monthAgo) {
        return coreServiceIntegration.getMostOrderedItems(monthAgo).stream()
                // группируем продукты с одинаковым названием и складываем количество
                .collect(Collectors.groupingBy(o -> o.getProduct().getTittle(), Collectors.summingInt(OrderItemDto::getQuantity)))
                .entrySet().stream()
                // берем из мапы объект и преобразуем в дто
                .map(p -> new ProductDto(p.getKey(), p.getValue()))
                // сортируем по количеству в заказах
                .sorted((o1, o2) -> o2.getCount() - o1.getCount())
                .limit(limit)
                .collect(Collectors.toList());
    }


    /*
    Возвращает самые добавляемые товары в корзину за последние сутки
     */
    public List<ProductDto> getMostAddedInCartProducts(int limit) {
        List<Long> allProductDtoIdList = coreServiceIntegration.getAllProducts().stream()
                .map(ProductDto::getId)
                .collect(Collectors.toList());

        return cartStatisticService.getStatistics(allProductDtoIdList).stream()
                .sorted(Comparator.comparingInt(ProductDto::getCount).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /*
    Возвращает статистику по добавляемым в корзину товарам за определенный день
     */
    public ProductsWithDailyStatDto getStatistics(Integer year, Integer month, Integer day) {
        return cartStatisticService.getStatistics(year, month, day);
    }


}
