package ru.tishin.springweb.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.api.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.core.entities.Product;
import ru.tishin.springweb.core.exceptions.ProductNotFoundException;
import ru.tishin.springweb.core.repository.OrderItemRepository;
import ru.tishin.springweb.core.repository.ProductRepository;
import ru.tishin.springweb.core.repository.specifications.ProductsSpecifications;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> find(Integer minCost, Integer maxCost, String partTittle, Integer page, String category) {
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
        if (category != null) {
            spec = spec.and(ProductsSpecifications.productCategory(category));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public Page<Product> find(Integer page, String category) {
        Specification<Product> spec = Specification.where(null);
        if (category != null) {
            spec = spec.and(ProductsSpecifications.productCategory(category));
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
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found. Id: " + id));
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

    /*
    @Transactional
    public List<Product> findFiveMostPopularProductsForLastMonth() {
        return orderItemRepository.findFiveMostPopularProductsInOrdersForLastMonth().stream()
                .map(this::findProductById).collect(Collectors.toList());
    }
     */


    @Transactional
    public String findTittleById(Long productId) {
        return productRepository.findTittleById(productId);
    }

    /*
    FOR SOAP

    public ru.tishin.springweb.soap.Product mapProductToProductSoap(Product product) {
        ru.tishin.springweb.soap.Product productSoap = new ru.tishin.springweb.soap.Product();
        productSoap.setId(product.getId());
        productSoap.setTittle(product.getTittle());
        productSoap.setCost(product.getCost());
        productSoap.setCategoryTittle(product.getCategory().getTittle());
        return productSoap;
    }

    public List<ru.tishin.springweb.soap.Product> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapProductToProductSoap).collect(Collectors.toList());
    }

    public ru.tishin.springweb.soap.Product getById(Long id) {
        return productRepository.findById(id).map(this::mapProductToProductSoap).orElseThrow(() -> new ResourceNotFoundException(
                "Продукт " + id + "не найден"));
    }
     */

}
