package ru.tishin.springweb.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tishin.springweb.core.entities.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // boolean existsByUsername(String username);

    @Query("select o from Order o where o.username = ?1")
    List<Order> findAllOrderByUsername(String username);
}
