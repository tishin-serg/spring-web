package ru.tishin.springweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.model.Product;
import ru.tishin.springweb.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository repository;

    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getCatalog() {
        return repository.getCatalog();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void changeCostById(Long id, Integer delta) {
        Product product = repository.findByID(id);
        product.setCost(product.getCost() + delta);
    }
}
