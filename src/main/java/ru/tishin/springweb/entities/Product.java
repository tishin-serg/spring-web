package ru.tishin.springweb.entities;

import ru.tishin.springweb.dto.ProductDto;

import javax.persistence.*;

@Entity
@Table(name = "catalog")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "tittle")
    private String tittle;
    @Column(name = "cost")
    private Integer cost;

    public Product() {
    }

    public Product(Long id, String tittle, Integer cost) {
        this.id = id;
        this.tittle = tittle;
        this.cost = cost;
    }

    public Product(ProductDto productDto) {
        this.id = productDto.getId();
        this.tittle = productDto.getTittle();
        this.cost = productDto.getCost();
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
        return "Product{" +
                "id=" + id +
                ", tittle='" + tittle + '\'' +
                ", cost=" + cost +
                '}';
    }
}
