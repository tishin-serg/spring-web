package ru.tishin.springweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tishin.springweb.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.cost > ?1")
    List<Product> findAllByCostMoreThan(Integer min);

    @Query("select p from Product p where p.cost < ?1")
    List<Product> findAllByCostLessThan(Integer max);

    List<Product> findAllByCostBetween(Integer min, Integer max);

    // так и не понял, как сделать инсерт с помощью hql. Поэтому нативный запрос.
    // Расскажите, пожалуйста, нужен ли тут hql и как с его помощью создать запись в бд
    @Modifying
    @Query(value = "INSERT INTO catalog (tittle, cost) VALUES (?1, ?2)", nativeQuery = true)
    void createProduct(String tittle, Integer cost);

}
