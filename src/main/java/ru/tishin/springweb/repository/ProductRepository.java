package ru.tishin.springweb.repository;

import org.springframework.stereotype.Component;
import ru.tishin.springweb.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRepository {
    private List<Product> catalog;

    @PostConstruct
    public void init() {
        catalog = new ArrayList<>(List.of(
                new Product(1L, "Milk", 10),
                new Product(2L, "Apple", 20),
                new Product(3L, "Banana", 30)
        ));
    }

    public List<Product> getCatalog() {
        return Collections.unmodifiableList(catalog);
    }

    public Product findById(Long id) {
        return catalog.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
