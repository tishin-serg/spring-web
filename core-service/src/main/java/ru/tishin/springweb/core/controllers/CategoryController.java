package ru.tishin.springweb.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tishin.springweb.api.dto.StringResponse;
import ru.tishin.springweb.core.entities.Category;
import ru.tishin.springweb.core.services.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Контроллер категорий продуктов", description = "Даёт доступ ко всем категориям продуктов")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Метод отдаёт все категории")
    public List<StringResponse> getCategories() {
       return categoryService.findAllCategories().stream()
               .map(c -> new StringResponse(c.getTittle()))
               .collect(Collectors.toList());
    }
}
