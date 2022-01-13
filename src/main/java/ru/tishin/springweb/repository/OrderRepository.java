package ru.tishin.springweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tishin.springweb.entities.Order;

import java.util.List;

//todo проверить с постмана работу метода existsByUsername

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllOrderByUserId(Long userId);

    // как правильно составить hql запрос на проверку существования заказа по username?
    @Query("from Order o where exists (select u from User u where u.username = ?1)")
    boolean existsByUsername(String username);

    @Query("select o from Order o where o.user.username = ?1")
    List<Order> findAllOrderByUsername(String username);
}
