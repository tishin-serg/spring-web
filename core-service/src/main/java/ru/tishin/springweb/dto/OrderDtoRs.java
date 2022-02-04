package ru.tishin.springweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDtoRs {
    private Long orderId;
    private String username;
    private String address;
    private String phone;
    private int totalPrice;
    private List<OrderItemDto> itemList;
}
