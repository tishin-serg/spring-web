package ru.tishin.springweb.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tishin.springweb.api.core.OrderDto;
import ru.tishin.springweb.api.core.OrderDtoRs;
import ru.tishin.springweb.api.core.OrderItemDto;
import ru.tishin.springweb.core.entities.Order;
import ru.tishin.springweb.core.entities.OrderItem;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final ProductConverter productConverter;

    public OrderItemDto toOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setOrder(fromEntityToDto(orderItem.getOrder()));
        orderItemDto.setProduct(orderItemDto.getProduct());
        orderItemDto.setPricePerProduct(orderItemDto.getPricePerProduct());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        return orderItemDto;
    }

    public OrderDto fromEntityToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setAddress(order.getAddress());
        orderDto.setPhone(order.getPhone());
        return orderDto;
    }

    public OrderDtoRs toOrderDtoRs(Order order) {
        return new OrderDtoRs(order.getId(),
                order.getUsername(),
                order.getAddress(),
                order.getPhone(),
                order.getTotalPrice(),
                order.getOrderItems().stream().map(this::toOrderItemDto).collect(Collectors.toList()));
    }
}
