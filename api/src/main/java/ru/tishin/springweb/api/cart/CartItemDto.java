package ru.tishin.springweb.api.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.tishin.springweb.api.core.ProductDto;

import java.math.BigDecimal;

@Schema(description = "Модель продукта в корзине")
public class CartItemDto {
    @Schema(description = "Идентификатор продукта", example = "1")
    private Long productId;
    @Schema(description = "Название продукта", example = "Milk")
    private String tittle;
    @Schema(description = "Количество продукта", example = "2")
    private int quantity;
    @Schema(description = "Цена за единицу продукта", example = "80.00")
    private BigDecimal pricePerProduct;
    @Schema(description = "Сумма за все продукты этого типа", example = "160.00")
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
