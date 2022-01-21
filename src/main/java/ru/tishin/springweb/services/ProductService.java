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

    @Transactional
    public String findTittleById(Long productId) {
        return productRepository.findTittleById(productId);
    }

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

    //    public static final Function<Product, ru.tishin.springweb.soap.origin.Product> functionEntityToSoap = productEntity -> {
//        ru.tishin.springweb.soap.origin.Product productSoap = new ru.tishin.springweb.soap.origin.Product();
//        productSoap.setId(productEntity.getId());
//        productSoap.setTittle(productEntity.getTittle());
//        productSoap.setCost(productEntity.getCost());
//        productSoap.setCategoryTittle(productEntity.getCategory().getTittle());
//        return productSoap;
//    };
//
//    public List<ru.tishin.springweb.soap.origin.Product> getAllProducts() {
//        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
//    }
//
//    public ru.tishin.springweb.soap.origin.Product getById(Long id) {
//        return productRepository.findById(id).map(functionEntityToSoap).orElseThrow(() -> new ResourceNotFoundException(
//                "Продукт " + id + "не найден"));
//    }
}
