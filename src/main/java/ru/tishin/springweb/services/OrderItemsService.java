package ru.tishin.springweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tishin.springweb.dto.Cart;
import ru.tishin.springweb.entities.OrderItem;
import ru.tishin.springweb.entities.User;
import ru.tishin.springweb.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.repository.OrderItemsRepository;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
должен сохранять ордер айтемы в репозиторий
 */

@Service
@RequiredArgsConstructor
public class OrderItemsService {
    private final CartService cartService;
    private final OrderItemsRepository orderItemsRepository;
    private final UserService userService;

    // Не знаю как тут сделать, чтобы было одно обращение в базу, а не по количеству продуктов
    // пробовал настроить batch_insert в пропертях, но почитал в доках, что сущности использующие GenerationType = IDENTITY
    // всё равно будут генерить на 1 вставку - 1 запрос из-за генерации id. Да и сам пробовал, не получилось
    // есть тут какое-то другое решение?

    @Transactional
    public void save(Long orderId, Long userId) {
        Cart cart = cartService.getCurrentCart();
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(i -> new OrderItem(i.getProductId(),
                        userId,
                        orderId,
                        i.getQuantity(),
                        i.getPricePerProduct(),
                        i.getPrice()))
                .collect(Collectors.toList());
        orderItemsRepository.saveAll(orderItems);
    }

    public List<OrderItem> getAllOrderItemsByUserId(Long userId) {
        return orderItemsRepository.findAllByUserId(userId);
    }
}
