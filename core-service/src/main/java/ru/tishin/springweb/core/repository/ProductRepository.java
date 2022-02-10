package ru.tishin.springweb.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tishin.springweb.core.entities.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "select tittle from products where id = :productId", nativeQuery = true)
    String findTittleById(Long productId);
}
