package ru.tishin.springweb.api.core;

import java.util.Objects;

public class ProductDto {

    private Long id;
    private String tittle;
    private Integer cost;
    private Integer count;

    public ProductDto(Long id, String tittle, Integer cost) {
        this.id = id;
        this.tittle = tittle;
        this.cost = cost;
    }

    public ProductDto(Long id, String tittle, Integer cost, Integer count) {
        this.id = id;
        this.tittle = tittle;
        this.cost = cost;
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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
