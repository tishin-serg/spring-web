package ru.tishin.springweb.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tishin.springweb.core.entities.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
