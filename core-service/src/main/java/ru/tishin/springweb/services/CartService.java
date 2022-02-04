package ru.tishin.springweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.dto.Cart;
import ru.tishin.springweb.entities.Product;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final RedisTemplate<String, Object> redisTemplate;

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
        Product product = productService.findProductById(productId);
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
