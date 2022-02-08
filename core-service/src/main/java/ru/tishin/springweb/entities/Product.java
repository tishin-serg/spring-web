package ru.tishin.springweb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "tittle")
    private String tittle;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "cost")
    private Integer cost;

    public Product(Long id, String tittle, Integer cost) {
        this.id = id;
        this.tittle = tittle;
        this.cost = cost;
    }
}
