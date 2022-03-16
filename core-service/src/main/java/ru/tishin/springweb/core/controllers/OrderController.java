package ru.tishin.springweb.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.api.cart.CartDto;
import ru.tishin.springweb.api.core.OrderDto;
import ru.tishin.springweb.api.core.OrderDtoRs;
import ru.tishin.springweb.api.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.core.converters.OrderConverter;
import ru.tishin.springweb.core.entities.Order;
import ru.tishin.springweb.core.integrations.CartServiceIntegration;
import ru.tishin.springweb.core.services.OrderService;
import ru.tishin.springweb.core.validators.OrderDtoValidator;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Контроллер заказов", description = "Отвечает за оформление заказов")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final OrderDtoValidator validator;
    private final CartServiceIntegration cartServiceIntegration;

    @PostMapping
    @Operation(summary = "Метод для создания заказа",
            responses = {
                    @ApiResponse(
                            description = "Идентификатор созданного заказа", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Long.class))
                    )
            })
    public Long createOrder(@RequestBody OrderDto orderDto, @RequestHeader String username) {
        validator.validate(orderDto);
        CartDto cartDto = cartServiceIntegration.getCurrentCart(username);
        if (cartDto.getItems().isEmpty()) {
            //todo переделать под другой exception
            throw new ResourceNotFoundException("Корзина не может быть пустой");
        }
        Order order = new Order(orderDto.getAddress(), orderDto.getPhone());
        return orderService.createOrder(order, username, cartDto);
    }

    @GetMapping
    @Operation(summary = "Метод отдает модель заказа со списком продуктов по имени пользователя")
    public List<OrderDtoRs> getOrderItems(@RequestHeader String username) {
        List<Order> orderList = orderService.findAllOrderByUsername(username);
        return orderList.stream().map(orderConverter::toOrderDtoRs).collect(Collectors.toList());
    }
}
