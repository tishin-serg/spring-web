package ru.tishin.springweb.core.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.core.converters.ProductConverter;
import ru.tishin.springweb.core.entities.Product;
import ru.tishin.springweb.core.services.OrderItemService;
import ru.tishin.springweb.core.services.ProductService;
import ru.tishin.springweb.core.validators.ProductValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final OrderItemService orderItemService;
    private final ProductService service;
    private final ProductValidator validator;
    private final ProductConverter productConverter;

    @GetMapping
    public Page<ProductDto> getProducts(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "tittle_part", required = false) String tittlePart,
            @RequestParam(name = "min_cost", required = false) Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost
    ) {
        if (page < 1) {
            page = 1;
        }
        return service.find(minCost, maxCost, tittlePart, page, category).map(productConverter::toProductDto);
    }

    /*
    Дописал этот метод, чтобы не возиться с Page на принимающей стороне
     */
    @GetMapping("/all")
    public List<ProductDto> getProducts() {
        return service.getProducts().stream().map(productConverter::toProductDto).collect(Collectors.toList());
    }

    @GetMapping("/category")
    public Page<ProductDto> getProductsByCategories(
            @RequestParam String category,
            @RequestParam(name = "p", defaultValue = "1") Integer page
    ) {
        if (page < 1) {
            page = 1;
        }
        return service.find(page, category).map(productConverter::toProductDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productConverter.toProductDto(service.findProductById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        validator.validate(productDto);
        productDto.setId(null);
        Product product = productConverter.fromProductDto(productDto);
        service.save(product);
        return productConverter.toProductDto(product);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        validator.validate(productDto);
        Product product = service.update(productDto);
        return productConverter.toProductDto(product);
    }
}
