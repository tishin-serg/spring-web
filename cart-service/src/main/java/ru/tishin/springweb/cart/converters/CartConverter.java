package ru.tishin.springweb.cart.converters;

import org.springframework.stereotype.Component;
import ru.tishin.springweb.api.cart.CartDto;
import ru.tishin.springweb.api.cart.CartItemDto;
import ru.tishin.springweb.api.cart.ProductsWithDailyStatDto;
import ru.tishin.springweb.cart.models.Cart;
import ru.tishin.springweb.cart.models.ProductsWithDailyStat;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {
    public CartDto entityToDto(Cart cart) {
        List<CartItemDto> dtoList = cart.getItems().stream()
                .map(cartItem -> new CartItemDto(
                        cartItem.getProductId(),
                        cartItem.getTittle(),
                        cartItem.getQuantity(),
                        cartItem.getPricePerProduct(),
                        cartItem.getPrice()))
                .collect(Collectors.toList());
        return new CartDto(dtoList, cart.getTotalPrice());
    }

    public ProductsWithDailyStatDto entityToDto(ProductsWithDailyStat productsWithDailyStat) {
        return new ProductsWithDailyStatDto(productsWithDailyStat.getProductDtoList(), productsWithDailyStat.getLocalDate());
    }
}
