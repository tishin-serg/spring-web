package ru.tishin.springweb.repository;

import ru.tishin.springweb.model.Product;

import java.util.List;

public interface ProductDaoInterface {
    Product findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
    void saveOrUpdate(Product product);
}
