package ru.tishin.springweb.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.tishin.springweb.api.dto.Cart;
import ru.tishin.springweb.api.dto.OrderItemDto;
import ru.tishin.springweb.entities.Order;
import ru.tishin.springweb.entities.Product;
import ru.tishin.springweb.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = OrderService.class)
class OrderServiceTest {

    /*
       Читал, что хорошая практика: 1 кейс - 1 тестовый метод.
       Но тогда получается много копирования кода, там где мокаем productservice.

       Как в таком случае поступать?

     */

    private OrderItemDto orderItemDto1;
    private OrderItemDto orderItemDto2;
    private Order order;

    @Autowired
    private OrderService orderService;

    @Spy
    private Cart cart;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void prepareData() {
        orderItemDto1 = new OrderItemDto(1L, "apple", 1, 10, 10);
        orderItemDto2 = new OrderItemDto(2L, "cucumber", 2, 10, 20);
        cart.getItems().add(orderItemDto1);
        cart.getItems().add(orderItemDto2);
        order = new Order();
        order.setAddress("Moscow");
        order.setPhone("555-555");
    }

    @AfterEach
    void clearCart() {
        cart.clear();
    }

    @Test
    void createOrder_ShouldTotalPriceFromCartEqualsInOrder() {
        Mockito
                .when(productService.findProductById(1L))
                .thenReturn(
                        new Product(orderItemDto1.getProductId(),
                                    orderItemDto1.getTittle(),
                                    orderItemDto1.getPricePerProduct()));

        Mockito
                .when(productService.findProductById(2L))
                .thenReturn(
                        new Product(orderItemDto2.getProductId(),
                                orderItemDto2.getTittle(),
                                orderItemDto2.getPricePerProduct()));

        Mockito.when(orderRepository.saveAndFlush(order)).thenReturn(order);
        orderService.createOrder(order, "bob", cart);
        Assertions.assertEquals(cart.getTotalPrice(), order.getTotalPrice());
    }

    @Test
    void createOrder_ShouldCartClearAfterOrderSave() {
        Mockito
                .when(productService.findProductById(1L))
                .thenReturn(
                        new Product(orderItemDto1.getProductId(),
                                orderItemDto1.getTittle(),
                                orderItemDto1.getPricePerProduct()));

        Mockito
                .when(productService.findProductById(2L))
                .thenReturn(
                        new Product(orderItemDto2.getProductId(),
                                orderItemDto2.getTittle(),
                                orderItemDto2.getPricePerProduct()));
        Mockito.when(orderRepository.saveAndFlush(order)).thenReturn(order);
        orderService.createOrder(order, "bob", cart);
        Assertions.assertTrue(cart.getItems().isEmpty());
    }
}