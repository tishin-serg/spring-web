package ru.tishin.springweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tishin.springweb.dto.ProductDto;
import ru.tishin.springweb.entities.Product;
import ru.tishin.springweb.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.repository.ProductRepository;
import ru.tishin.springweb.repository.specifications.ProductsSpecifications;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> find(Integer minCost, Integer maxCost, String partTittle, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minCost != null) {
            spec = spec.and(ProductsSpecifications.greaterThanOrEqualTo(minCost));
        }
        if (maxCost != null) {
            spec = spec.and(ProductsSpecifications.lessThanOrEqualTo(maxCost));
        }
        if (partTittle != null) {
            spec = spec.and(ProductsSpecifications.tittleLike(partTittle));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found. Id: " + id));
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = findProductById(productDto.getId());
        product.setTittle(productDto.getTittle());
        product.setCost(productDto.getCost());
        return product;
    }

}
