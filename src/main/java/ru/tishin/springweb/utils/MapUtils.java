package ru.tishin.springweb.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.dto.OrderDtoRs;
import ru.tishin.springweb.dto.OrderItemDto;
import ru.tishin.springweb.dto.ProductDto;
import ru.tishin.springweb.dto.UserDto;
import ru.tishin.springweb.entities.Order;
import ru.tishin.springweb.entities.OrderItem;
import ru.tishin.springweb.entities.Product;
import ru.tishin.springweb.entities.User;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapUtils {

    public Product fromProductDto(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTittle(), productDto.getCost());
    }

    public ProductDto toProductDto(Product product) {
        return new ProductDto(product.getId(), product.getTittle(), product.getCost());
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getUsername(), user.getPassword(), user.getEmail());
    }

    public User fromUserDto(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
    }

    public OrderItemDto toOrderItemDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getTittle(), orderItem.getQuantity(),
                orderItem.getPricePerProduct(), orderItem.getPrice());
    }

    public OrderDtoRs toOrderDtoRs(Order order) {
        return new OrderDtoRs(order.getId(),
                order.getUser().getUsername(),
                order.getAddress(),
                order.getPhone(),
                order.getTotalPrice(),
                order.getOrderItems().stream()
                        .map(this::toOrderItemDto)
                        .collect(Collectors.toList()));
    }
}
