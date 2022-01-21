package ru.tishin.springweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tishin.springweb.dto.StringResponse;
import ru.tishin.springweb.services.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<StringResponse> getCategories() {
       return categoryService.findAllCategories().stream()
               .map(c -> new StringResponse(c.getTittle()))
               .collect(Collectors.toList());
    }
}
