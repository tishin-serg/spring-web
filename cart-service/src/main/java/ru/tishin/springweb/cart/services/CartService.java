package ru.tishin.springweb.cart.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.cart.integrations.ProductServiceIntegration;
import ru.tishin.springweb.cart.models.Cart;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final CartStatisticService cartStatisticService;
    private final RedisTemplate<String, Object> redisCartTemplate;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisCartTemplate.hasKey(cartKey)) {
            redisCartTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisCartTemplate.opsForValue().get(cartKey);
    }

    public void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        if (cart == null) return;
        action.accept(cart);
        updateCart(cartKey, cart);
    }

    public void addProduct(String cartKey, Long productId) {
        ProductDto productDto = productServiceIntegration.findById(productId);
        execute(cartKey, c -> c.addProduct(productDto));
        cartStatisticService.add(productDto);
    }

    public void decreaseProduct(String cartKey, Long productId) {
        execute(cartKey, c -> c.decreaseProduct(productId));
        cartStatisticService.decrease(productId);
    }

    public void removeProduct(String cartKey, Long productId) {
        execute(cartKey, c -> c.removeProduct(productId));
        cartStatisticService.removeProductFromTargetCart(getCurrentCart(cartKey), productId);
    }

    public void clearCart(String cartKey) {
        cartStatisticService.removeProductsFromTargetCart(getCurrentCart(cartKey));
        execute(cartKey, Cart::clear);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisCartTemplate.opsForValue().set(cartKey, cart);
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
        cartStatisticService.changeProductsCount(productId, 1);
    }

    public void fillCart() {
        for (int i = 1; i <= 20; i++) { // бежим по списку продуктов
            for (int j = 0; j < 10; j++) { // бежим по списку корзин
                for (int k = 0; k < (int) (Math.random() * 20); k++) { // добавляем случайное количество раз
                    addProduct("#" + j, (long) i);
                }
            }
        }
    }

}
