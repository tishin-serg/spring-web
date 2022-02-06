package ru.tishin.springweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.tishin.springweb.api.dto.Cart;
import ru.tishin.springweb.api.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.dto.OrderDto;
import ru.tishin.springweb.dto.OrderDtoRs;
import ru.tishin.springweb.entities.Order;
import ru.tishin.springweb.services.OrderService;
import ru.tishin.springweb.utils.MapUtils;
import ru.tishin.springweb.validators.OrderDtoValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final static String URL_CART_MS = "http://localhost:8187/web-market-cart/api/v1/carts";
    private final OrderService orderService;
    private final MapUtils mapUtils;
    private final OrderDtoValidator validator;
    private final RestTemplate restTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createOrder(@RequestBody OrderDto orderDto, @RequestHeader String username) {
        validator.validate(orderDto);

        String uuid = restTemplate.getForObject(URL_CART_MS + "/uuid/{username}",
                String.class, username);
        Cart cart = restTemplate.getForObject(URL_CART_MS + "/{uuid}", Cart.class, uuid);

        if (cart == null) throw new ResourceNotFoundException("Корзины с ключом " + uuid + " не существует");

        if (cart.getItems().isEmpty()) {
            throw new ResourceNotFoundException("Корзина не может быть пустой");
        }
        Order order = new Order(orderDto.getAddress(), orderDto.getPhone());
        return orderService.createOrder(order, username, cart);
    }

    @GetMapping
    public List<OrderDtoRs> getOrderItems(@RequestHeader String username) {
//        if (!orderService.existsByUsername(username)) {
//            throw new ResourceNotFoundException("Заказы для клиента " + username + " не найдены");
//        }
        List<Order> orderList = orderService.findAllOrderByUsername(username);
        return orderList.stream().map(mapUtils::toOrderDtoRs).collect(Collectors.toList());
    }
}
