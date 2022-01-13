package ru.tishin.springweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.dto.OrderDto;
import ru.tishin.springweb.dto.OrderDtoRs;
import ru.tishin.springweb.entities.Order;
import ru.tishin.springweb.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.services.CartService;
import ru.tishin.springweb.services.OrderService;
import ru.tishin.springweb.services.UserService;
import ru.tishin.springweb.utils.MapUtils;
import ru.tishin.springweb.validators.OrderDtoValidator;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MapUtils mapUtils;
    private final CartService cartService;
    private final OrderDtoValidator validator;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createOrder(@RequestBody OrderDto orderDto, Principal principal) {
        validator.validate(orderDto);
        if (cartService.getCurrentCart().getItems().isEmpty()) {
            throw new ResourceNotFoundException("Корзина не может быть пустой");
        }
        Order order = new Order(orderDto.getAddress(), orderDto.getPhone());
        return orderService.createOrder(order, principal.getName());
    }

    @GetMapping
    public List<OrderDtoRs> getOrderItems(Principal principal) {
        String username = principal.getName();
        if (!userService.existsUsername(username)) {
            throw new ResourceNotFoundException("Клиент " + username + " не найден");
        }
//        if (!orderService.existsByUsername(username)) {
//            throw new ResourceNotFoundException("Заказы для клиента " + username + " не найдены");
//        }
        List<Order> orderList = orderService.findAllOrderByUsername(username);
        return orderList.stream().map(mapUtils::toOrderDtoRs).collect(Collectors.toList());
    }
}
