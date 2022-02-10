package ru.tishin.springweb.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tishin.springweb.api.cart.CartDto;
import ru.tishin.springweb.core.entities.Order;
import ru.tishin.springweb.core.entities.OrderItem;
import ru.tishin.springweb.core.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Transactional
    public Long createOrder(Order order, String username, CartDto cart) {
        order.setTotalPrice(cart.getTotalPrice());
        order.setUsername(username);
        order.setOrderItems(cart.getItems().stream()
                .map(cartItemDto -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setPrice(cartItemDto.getPrice());
                    item.setProduct(productService.findProductById(cartItemDto.getProductId()));
                    item.setPricePerProduct(cartItemDto.getPricePerProduct());
                    item.setQuantity(cartItemDto.getQuantity());
                    return item;
                })
                .collect(Collectors.toList()));
        Long orderId = orderRepository.saveAndFlush(order).getId();
        cart.clear();
        return orderId;
    }

    public List<Order> findAllOrderByUsername(String username) {
        return orderRepository.findAllOrderByUsername(username);
    }
}
