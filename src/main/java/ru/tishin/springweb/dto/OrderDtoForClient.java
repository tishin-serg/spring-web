package ru.tishin.springweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/*
чтобы показать инфу по заказу и его содержимое клиенту
 */
@Data
@AllArgsConstructor
public class OrderDtoForClient {
    private Long orderId;
    private String address;
    private String phone;
    private int totalPrice;
    private List<OrderItemDto> itemList;
}
