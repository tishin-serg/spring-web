package ru.tishin.springweb.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель заказа")
public class OrderDto {

    @Schema(description = "Адрес", example = "Москва, Тверская 4, 55")
    private String address;

    @Schema(description = "Номер телефона", example = "8-999-666-11-22")
    private String phone;

    public OrderDto(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }

    public OrderDto() {
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
}
