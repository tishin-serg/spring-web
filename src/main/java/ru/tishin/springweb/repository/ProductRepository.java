package ru.tishin.springweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tishin.springweb.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // @Query("select p from Product p where p.cost > ?1")
    List<Product> findAllByCostGreaterThan(Integer min);

    // @Query("select p from Product p where p.cost < ?1")
    List<Product> findAllByCostLessThan(Integer max);

    List<Product> findAllByCostBetween(Integer min, Integer max);

}
