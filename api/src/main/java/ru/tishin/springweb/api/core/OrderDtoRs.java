package ru.tishin.springweb.api.core;

import java.util.List;

public class OrderDtoRs {
    private Long orderId;
    private String username;
    private String address;
    private String phone;
    private int totalPrice;
    private List<OrderItemDto> itemList;

    public OrderDtoRs(Long orderId, String username, String address, String phone, int totalPrice, List<OrderItemDto> itemList) {
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItemDto> itemList) {
        this.itemList = itemList;
    }
}
