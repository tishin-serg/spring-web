package ru.tishin.springweb.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель продукта в заказе")
public class OrderItemDto {

    @Schema(description = "ID продукта", required = true, example = "1")
    private Long id;

    @Schema(description = "Модель продукта", required = true)
    private ProductDto product;

    @Schema(description = "Модель заказа", required = true)
    private OrderDto order;

    @Schema(description = "Количество единиц продукта в заказе", required = true)
    private int quantity;

    @Schema(description = "Цена за единицу продукт", required = true, example = "120.00")
    private BigDecimal pricePerProduct;

    @Schema(description = "Сумма в заказе всех продуктов этого типа", required = true, example = "1000.00")
    private BigDecimal price;

    public OrderItemDto(Long id, ProductDto product, OrderDto order, int quantity, BigDecimal pricePerProduct, BigDecimal price) {
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
