package ru.tishin.springweb.dto;

import ru.tishin.springweb.entities.Product;

public class ProductDto {

    private Long id;
    private String tittle;
    private Integer cost;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.tittle = product.getTittle();
        this.cost = product.getCost();
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

    @Override
    public String toString() {
        return "Product{ tittle='" + tittle + '\'' + ", cost=" + cost + '}';
    }
}
