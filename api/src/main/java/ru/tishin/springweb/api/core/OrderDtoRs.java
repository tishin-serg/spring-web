package ru.tishin.springweb.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Модель заказа в ответ на запрос")
public class OrderDtoRs {

    @Schema(description = "Идентификатор заказа", example = "5")
    private Long orderId;

    @Schema(description = "Имя клиента", example = "Bob")
    private String username;

    @Schema(description = "Адрес", example = "Москва, Тверская 4, 55")
    private String address;

    @Schema(description = "Номер телефона", example = "8-999-666-22-11")
    private String phone;

    @Schema(description = "Сумма заказа", example = "1000.00")
    private BigDecimal totalPrice;

    @Schema(description = "Список моделей продукта в заказе")
    private List<OrderItemDto> itemList;

    public OrderDtoRs(Long orderId, String username, String address, String phone, BigDecimal totalPrice, List<OrderItemDto> itemList) {
        this.orderId = orderId;
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.totalPrice = totalPrice;
        this.itemList = itemList;
    }

    public OrderDtoRs() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItemDto> itemList) {
        this.itemList = itemList;
    }
}
