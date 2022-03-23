package ru.tishin.springweb.api.cart;

import ru.tishin.springweb.api.core.ProductDto;

import java.math.BigDecimal;

public class CartItemDto {
    private Long productId;
    private String tittle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;

    public CartItemDto(Long productId, String tittle, int quantity, BigDecimal pricePerProduct, BigDecimal price) {
        this.productId = productId;
        this.tittle = tittle;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }

    public CartItemDto(ProductDto productDto) {
        this.productId = productDto.getId();
        this.tittle = productDto.getTittle();
        this.quantity = 1;
        this.pricePerProduct = productDto.getCost();
        this.price = productDto.getCost();
    }

    public CartItemDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
