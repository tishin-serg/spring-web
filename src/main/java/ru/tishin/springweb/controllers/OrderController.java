package ru.tishin.springweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.dto.OrderDto;
import ru.tishin.springweb.entities.Order;
import ru.tishin.springweb.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.services.CartService;
import ru.tishin.springweb.services.OrderService;
import ru.tishin.springweb.services.UserService;
import ru.tishin.springweb.utils.MapUtils;
import ru.tishin.springweb.validators.OrderDtoValidator;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MapUtils mapUtils;
    private final CartService cartService;
    private final OrderDtoValidator validator;
    private final UserService userService;

    // получить актуальные заказы оформленные юзером

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto, Principal principal) {
        validator.validate(orderDto);
        if (cartService.getCurrentCart().getItems().isEmpty()) {
            throw new ResourceNotFoundException("Корзина не может быть пустой");
        }
        Order order = new Order(orderDto.getAddress(), orderDto.getPhone());
        return orderService.createOrder(order, principal);
    }


    // Какой тут подход оптимальнее?
    // Достать optional<order> и если не найдется заказ кинуть исключение, или проверить наличие заказов по userid и в случае
    // отсутствия заказа просто вернуть статус код с ошибкой?
    @GetMapping
    public ResponseEntity<?> getOrderItems(Principal principal) {
        Long userId = userService.findIdByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException(
                "Клиент " + principal.getName() + " не найден"));
        // Order order = orderService.findOrderByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Заказы для
        // клиента " + principal.getName() + " не найдены"));
        if (!orderService.existsByUserId(userId)) {
            return new ResponseEntity<>("Заказы для клиента " + principal.getName() + " не найдены", HttpStatus.NOT_FOUND);
        }
        Order order = orderService.findOrderByUserId(userId).get();
        return ResponseEntity.ok(mapUtils.toOrderDtoForClient(order));
    }
}
