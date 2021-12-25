package ru.tishin.springweb.utils;

import org.springframework.stereotype.Service;
import ru.tishin.springweb.dto.ProductDto;
import ru.tishin.springweb.dto.UserDto;
import ru.tishin.springweb.entities.Product;
import ru.tishin.springweb.entities.User;

@Service
public class MapUtils {
    public static Product fromProductDto(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTittle(), productDto.getCost());
    }

    public static ProductDto toProductDto(Product product) {
        return new ProductDto(product.getId(), product.getTittle(), product.getCost());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getUsername(), user.getPassword(), user.getEmail());
    }

    public static User fromUserDto(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
    }

}
