package ru.tishin.springweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tishin.springweb.dto.Cart;
import ru.tishin.springweb.entities.Order;
import ru.tishin.springweb.entities.User;
import ru.tishin.springweb.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.repository.OrderRepository;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemsService orderItemsService;
    private final UserService userService;
    private final CartService cartService;

    @Transactional
    public ResponseEntity<?> createOrder(Order order, Principal principal) {
        User user = userService.findByUserName(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User: " + principal.getName() + " not found"));
        Cart cart = cartService.getCurrentCart();
        order.setTotalPrice(cart.getTotalPrice());
        order.setUserId(user.getId());
        // делаю так, чтобы вернуть на фронт id заказа сразу же после оформления
        Long orderId = orderRepository.saveAndFlush(order).getId();
        orderItemsService.save(orderId, user.getId());
        cart.clear();
        return ResponseEntity.ok(orderId); // возвращаем Id заказа на фронт
    }


    public Optional<Order> findOrderByUserId(Long userId) {
        return orderRepository.findOrderByUserId(userId);
    }

    public boolean existsByUserId(Long userId) {
        return orderRepository.existsByUserId(userId);
    }
}
