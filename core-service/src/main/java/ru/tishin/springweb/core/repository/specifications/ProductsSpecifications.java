package ru.tishin.springweb.core.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.tishin.springweb.core.entities.Product;

public class ProductsSpecifications {
    public static Specification<Product> greaterThanOrEqualTo(Integer minCost) {
        return (product, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(product.get("cost"), minCost);
    }

    public static Specification<Product> lessThanOrEqualTo(Integer maxCost) {
        return (product, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(product.get("cost"), maxCost);
    }

    public static Specification<Product> tittleLike(String partTittle) {
        return (product, query, criteriaBuilder) -> criteriaBuilder.like(product.get("tittle"), String.format("%%%s%%", partTittle));
    }

    public static Specification<Product> productCategory(String category) {
        return (product, query, criteriaBuilder) -> criteriaBuilder.equal(product.get("category").get("tittle"), category);
    }

}
