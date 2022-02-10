package ru.tishin.springweb.api.core;


public class OrderDto {
    private String address;
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
