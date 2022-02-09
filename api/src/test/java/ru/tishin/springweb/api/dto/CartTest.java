package ru.tishin.springweb.api.dto;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Cart.class)
class CartTest {

    @Spy
    private Cart cart; // 1450

    @BeforeEach
    public void fillCart() {
        for (int i = 0; i < 10; i++) {
            ProductDto productDto = new ProductDto();
            productDto.setId((long) (i + 1));
            productDto.setCost(100 + i * 10);
            productDto.setTittle("Product " + i);
            cart.getItems().add(new OrderItemDto(productDto));
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
        productDto.setCost(100);
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
        cart.addProduct(new ProductDto(10L, "Product 9", 190));
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
            productDto.setCost(100 + i * 10);
            productDto.setTittle("Product " + i);
            guestCart.addProduct(productDto);
        }

        cart.merge(guestCart);
        Assertions.assertEquals(2050, cart.getTotalPrice());
//        Assertions.assertEquals(0, guestCart.getTotalPrice());
//        Assertions.assertEquals(10, cart.getItems().size());

    }



}