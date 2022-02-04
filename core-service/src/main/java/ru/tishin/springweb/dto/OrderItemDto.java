package ru.tishin.springweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tishin.springweb.entities.Product;

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

    public OrderItemDto(Product product) {
        this.productId = product.getId();
        this.tittle = product.getTittle();
        this.quantity = 1;
        this.pricePerProduct = product.getCost();
        this.price = product.getCost();
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
        this.price = this.pricePerProduct * this.quantity;
    }
}
