package ru.tishin.springweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tishin.springweb.dto.Cart;
import ru.tishin.springweb.entities.Order;
import ru.tishin.springweb.entities.OrderItem;
import ru.tishin.springweb.entities.User;
import ru.tishin.springweb.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;

    @Transactional
    public Long createOrder(Order order, String username) {
        User user = userService.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User: " + username + " not found"));
        Cart cart = cartService.getCurrentCart(cartService.getCartUuidFromSuffix(username));
        order.setTotalPrice(cart.getTotalPrice());
        order.setUser(user);
        order.setOrderItems(cart.getItems().stream()
                .map(orderItemDto -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setPrice(orderItemDto.getPrice());
                    item.setProduct(productService.findProductById(orderItemDto.getProductId()));
                    item.setPricePerProduct(orderItemDto.getPricePerProduct());
                    item.setQuantity(orderItemDto.getQuantity());
                    return item;
                })
                .collect(Collectors.toList()));
        Long orderId = orderRepository.saveAndFlush(order).getId();
        // orderItemsService.save(order);
        cart.clear();
        return orderId;
    }


    public List<Order> findAllOrderByUserId(Long userId) {
        return orderRepository.findAllOrderByUserId(userId);
    }

//    public boolean existsByUsername(String username) {
//        return orderRepository.existsByUsername(username);
//    }

    public List<Order> findAllOrderByUsername(String username) {
        return orderRepository.findAllOrderByUsername(username);
    }
}
