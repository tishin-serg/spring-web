package ru.tishin.springweb.api.core;


public class OrderItemDto {
    private Long id;
    private ProductDto product;
    private OrderDto order;
    private int quantity;
    private int pricePerProduct;
    private int price;

    public OrderItemDto(Long id, ProductDto product, OrderDto order, int quantity, int pricePerProduct, int price) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }

    public OrderItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
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
