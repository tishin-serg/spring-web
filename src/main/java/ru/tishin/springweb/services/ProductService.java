package ru.tishin.springweb.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tishin.springweb.entities.Product;
import ru.tishin.springweb.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.repository.ProductRepository;
import ru.tishin.springweb.repository.specifications.ProductsSpecifications;

import java.util.List;

// Вопрос. На каком уровне мы должны преобразовывать объекты в ДТО? На сервисном или на уровне контроллеров?

@Service
public class ProductService {

    private ProductRepository productRepository;
    private final int pageSize = 5;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Это единственный верный способ создания спецификации или есть ещё покороче?
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
        return productRepository.findAll(spec, PageRequest.of(page - 1, pageSize));
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

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

}
