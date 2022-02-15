package ru.tishin.springweb.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.api.core.OrderItemDto;
import ru.tishin.springweb.core.converters.OrderConverter;
import ru.tishin.springweb.core.services.OrderItemService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;
    private final OrderConverter orderConverter;


    /*
    Возвращает List<OrderItemDto> за последние @monthAgo месяцев
     */
    @GetMapping
    public List<OrderItemDto> getAllOrderItemsForDefinedMonth(@RequestParam int monthAgo) {
        return orderItemService.findOrderItemsForPeriodAgo(monthAgo).stream()
                .map(orderConverter::toOrderItemDto)
                .collect(Collectors.toList());
    }


    //    @GetMapping
//    public List<OrderItemDto> getAllOrderItems() {
//        return orderItemService.findAllOrderItems().stream().map(orderConverter::toOrderItemDto).collect(Collectors.toList());


//    }

}
