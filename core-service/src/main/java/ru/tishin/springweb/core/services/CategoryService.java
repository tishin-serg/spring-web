package ru.tishin.springweb.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.core.entities.Category;
import ru.tishin.springweb.core.repository.CategoryRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}
