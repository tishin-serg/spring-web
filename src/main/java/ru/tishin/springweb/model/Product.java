package ru.tishin.springweb.model;

import javax.persistence.*;

// Не стал менять имя и тип переменной на int price, как в задании, чтобы не заморачиваться с переименованием
// Здесь принцип тот же

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
}
