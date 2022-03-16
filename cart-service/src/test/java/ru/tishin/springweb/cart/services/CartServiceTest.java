package ru.tishin.springweb.cart.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.client.RestTemplate;
import ru.tishin.springweb.cart.models.Cart;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.cart.models.CartItem;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = CartService.class)
class CartServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockBean
    private ValueOperations<String, Object> valueOperations;

    @Spy
    private Cart cart;

    @Autowired
    private CartService cartService;

    @BeforeEach
    void fillCart() {
        cart.getItems().add(new CartItem(1L, "apple", 1, BigDecimal.valueOf(10), BigDecimal.valueOf(10)));
        cart.getItems().add(new CartItem(2L, "cucumber", 2, BigDecimal.valueOf(10), BigDecimal.valueOf(20)));
    }

    @AfterEach
    void clearCart() {
        cart.clear();
    }

    @Test
    void addProduct() {

        // Возможно тут перемудрил с эмуляцией действий редиса, но по-другому не получалось никак

        Mockito.when(restTemplate.getForObject(any(), any(), eq(1L))).thenReturn(new ProductDto(1L, "apple", BigDecimal.valueOf(10)));
        Mockito.doReturn(true).when(redisTemplate).hasKey("cartKey");
        Mockito.doReturn(valueOperations).when(redisTemplate).opsForValue();
        Mockito.doReturn(cart).when(valueOperations).get("cartKey");
        cartService.addProduct("cartKey", 1L);
        // Mockito.verify(cart).addProduct(1L);
        Assertions.assertEquals(40, cart.getTotalPrice());
    }

}