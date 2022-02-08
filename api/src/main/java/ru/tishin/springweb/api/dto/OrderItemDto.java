package ru.tishin.springweb.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String tittle;
    private int quantity;
    private int pricePerProduct;
    private int price;

    public OrderItemDto(Long productId, String tittle, int quantity, int pricePerProduct, int price) {
        this.productId = productId;
        this.tittle = tittle;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }

    public OrderItemDto(ProductDto productDto) {
        this.productId = productDto.getId();
        this.tittle = productDto.getTittle();
        this.quantity = 1;
        this.pricePerProduct = productDto.getCost();
        this.price = productDto.getCost();
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
        this.price = this.pricePerProduct * this.quantity;
    }
}
