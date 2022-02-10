package ru.tishin.springweb.cart.models;

import ru.tishin.springweb.api.core.ProductDto;

public class CartItem {
    private Long productId;
    private String tittle;
    private int quantity;
    private int pricePerProduct;
    private int price;

    public CartItem(Long productId, String tittle, int quantity, int pricePerProduct, int price) {
        this.productId = productId;
        this.tittle = tittle;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }

    public CartItem(ProductDto productDto) {
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

    public CartItem() {
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

    public int getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(int pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
