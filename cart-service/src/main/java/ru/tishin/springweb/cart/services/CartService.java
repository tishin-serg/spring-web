package ru.tishin.springweb.cart.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.tishin.springweb.api.dto.ProductDto;
import ru.tishin.springweb.api.dto.Cart;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RestTemplate restTemplate;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        updateCart(cartKey, cart);
    }

    public void addProduct(String cartKey, Long productId) {
        ProductDto product = restTemplate.getForObject("http://localhost:8189/web-market-core/api/v1/products/{productId}",
                ProductDto.class, productId);
        execute(cartKey, c -> c.addProduct(product));
    }

    public void decreaseProduct(String cartKey, Long productId) {
        execute(cartKey, c -> c.decreaseProduct(productId));
    }

    public void removeProduct(String cartKey, Long productId) {
        execute(cartKey, c -> c.removeProduct(productId));
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart userCart = getCurrentCart(userCartKey);
        Cart guestCart = getCurrentCart(guestCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    public void increaseProduct(String cartKey, Long productId) {
        execute(cartKey, c -> c.addProduct(productId));
    }
}
