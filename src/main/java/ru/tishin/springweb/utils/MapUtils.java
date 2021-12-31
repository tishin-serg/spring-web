package ru.tishin.springweb.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.dto.OrderDtoForClient;
import ru.tishin.springweb.dto.OrderItemDto;
import ru.tishin.springweb.dto.ProductDto;
import ru.tishin.springweb.dto.UserDto;
import ru.tishin.springweb.entities.Order;
import ru.tishin.springweb.entities.OrderItem;
import ru.tishin.springweb.entities.Product;
import ru.tishin.springweb.entities.User;
import ru.tishin.springweb.services.OrderItemsService;
import ru.tishin.springweb.services.ProductService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapUtils {
    private final OrderItemsService orderItemsService;
    private final ProductService productService;

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
        return new OrderItemDto(orderItem.getProductId(), productService.findTittleById(orderItem.getProductId()), orderItem.getQuantity(),
                orderItem.getPricePerProduct(), orderItem.getPrice());
    }

    public OrderDtoForClient toOrderDtoForClient(Order order) {
        return new OrderDtoForClient(order.getId(),
                order.getAddress(),
                order.getPhone(),
                order.getTotalPrice(),
                orderItemsService.getAllOrderItemsByUserId(order.getId())
                        .stream()
                        .map(this::toOrderItemDto)
                        .collect(Collectors.toList()));
    }

}
