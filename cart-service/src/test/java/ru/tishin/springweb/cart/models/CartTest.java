package ru.tishin.springweb.cart.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tishin.springweb.api.core.ProductDto;

import java.math.BigDecimal;

@SpringBootTest(classes = Cart.class)
class CartTest {

    @Spy
    private Cart cart; // 1450

    @BeforeEach
    public void fillCart() {
        for (int i = 0; i < 10; i++) {
            ProductDto productDto = new ProductDto();
            productDto.setId((long) (i + 1));
            int x = 10 * i;
            productDto.setCost(BigDecimal.valueOf(100).add(BigDecimal.valueOf(x)));
            productDto.setTittle("Product " + i);
            cart.getItems().add(new CartItem(productDto));
        }
    }

    @Test
    void clear() {
        cart.clear();
        Assertions.assertTrue(cart.getItems().isEmpty());
        Assertions.assertEquals(0, cart.getTotalPrice());
    }

    @AfterEach
    public void clearCart() {
        cart.clear();
    }

    @Test
    void testAddProduct() {
        Assertions.assertTrue(cart.addProduct(1L));
        Mockito.verify(cart, Mockito.times(1)).recalculate();
    }

    @Test
    void addProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setId((long) (1));
        productDto.setCost(BigDecimal.valueOf(100));
        productDto.setTittle("Product " + 1);

        for (int i = 0; i < 5; i++) {
            cart.addProduct(productDto);
        }

        Assertions.assertEquals(6, cart.getItems().get(0).getQuantity());
//        Mockito.verify(cart, Mockito.times(5)).recalculate();
    }

    @Test
    void recalculate() {
        fillCart();
        cart.recalculate();
        Assertions.assertEquals(2900, cart.getTotalPrice());
    }

    @Test
    public void cartFillingTest() {
        Assertions.assertEquals(10, cart.getItems().size());
    }

    @Test
    void removeProduct() {
        cart.addProduct(new ProductDto(10L, "Product 9", BigDecimal.valueOf(190)));
        cart.removeProduct(10L);
//        Mockito.verify(cart, Mockito.times(2)).recalculate();
        Assertions.assertEquals(9, cart.getItems().size());
    }

    @Test
    void decreaseProduct() {
        cart.addProduct(1L);
        cart.decreaseProduct(1L);
        Assertions.assertEquals(10, cart.getItems().size());
//        Mockito.verify(cart, Mockito.times(2)).recalculate();
    }

    @Test
    void merge() {
        Cart guestCart = new Cart();
        for (int i = 0; i < 5; i++) {
            ProductDto productDto = new ProductDto();
            productDto.setId((long) (i + 1));
            int x = 10 * i;
            productDto.setCost(BigDecimal.valueOf(100).add(BigDecimal.valueOf(x)));
            productDto.setTittle("Product " + i);
            guestCart.addProduct(productDto);
        }

        cart.merge(guestCart);
        Assertions.assertEquals(2050, cart.getTotalPrice());
//        Assertions.assertEquals(0, guestCart.getTotalPrice());
//        Assertions.assertEquals(10, cart.getItems().size());

    }
}