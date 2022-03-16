package ru.tishin.springweb.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(description = "Модель продукта")
public class ProductDto {

    @Schema(description = "ID продукта", required = true)
    private Long id;

    @Schema(description = "Название продукта", required = true, maxLength = 255, minLength = 3, example = "Milk")
    private String tittle;

    @Schema(description = "Цена продукта", required = true, example = "120.00")
    private BigDecimal cost;

    @Schema(description = "Количество единиц продукта. Используется только для сбора статистики", hidden = true)
    private Integer count;

    public ProductDto(Long id, String tittle, BigDecimal cost) {
        this.id = id;
        this.tittle = tittle;
        this.cost = cost;
    }

    public ProductDto(Long id, String tittle, BigDecimal cost, Integer count) {
        this.id = id;
        this.tittle = tittle;
        this.cost = cost;
        this.count = count;
    }

    public ProductDto(String tittle, Integer count) {
        this.tittle = tittle;
        this.count = count;
    }

    public ProductDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
