package ru.tishin.springweb.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tishin.springweb.entities.Product;
import ru.tishin.springweb.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getCatalog() {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void changeCostById(Long id, Integer delta) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found. " +
                "Id: " + id));

        product.setCost(product.getCost() + delta);
        productRepository.save(product);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found. Id: " + id));
    }

    public List<Product> findAllByCostMoreThan(Integer min) {
        return productRepository.findAllByCostGreaterThan(min);
    }

    public List<Product> findAllByCostLessThan(Integer max) {
        return productRepository.findAllByCostLessThan(max);
    }

    public List<Product> findAllByCostBetween(Integer min, Integer max) {
        return productRepository.findAllByCostBetween(min, max);
    }

    @Transactional
    public void addNewProduct(Product product) {
        productRepository.save(product);
    }

}
