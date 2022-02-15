package ru.tishin.springweb.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.core.entities.OrderItem;
import ru.tishin.springweb.core.repository.OrderItemRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    /*
    Один из вариантов решения, как достать самые покупаемые продукты за месяц с помощью выборки из БД
    Т.е. находим самые покупаемые продукты и отдаём уже готовый результат

    public List<Long> findIdFiveMostPopularProductsId() {
        return orderItemRepository.findFiveMostPopularProductsInOrdersForLastMonth();
    }
     */

    public List<OrderItem> findAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public List<OrderItem> findOrderItemsForPeriodAgo(int monthAgo) {
        return orderItemRepository.findByCreatedAtAfter(LocalDateTime.now().minusMonths(monthAgo));
    }
}
