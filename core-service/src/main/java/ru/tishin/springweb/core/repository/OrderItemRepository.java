package ru.tishin.springweb.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tishin.springweb.core.entities.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /*String query = "select product_id, sum(quantity) \n" +
            "from order_items \n" +
            "where created_at > current_date - 30 \n" +
            "group by product_id \n" +
            "order by sum(quantity) desc \n" +
            "limit 5;";

    @Query(value = query, nativeQuery = true)
    List<Long> findFiveMostPopularProductsInOrdersForLastMonth();*/

    List<OrderItem> findByCreatedAtAfter(LocalDateTime dateTime);
}
