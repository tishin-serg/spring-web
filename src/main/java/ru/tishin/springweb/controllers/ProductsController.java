package ru.tishin.springweb.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.dto.ProductDto;
import ru.tishin.springweb.entities.Product;
import ru.tishin.springweb.services.Cart;
import ru.tishin.springweb.services.ProductService;
import ru.tishin.springweb.services.utils.MapUtils;
import ru.tishin.springweb.validators.ProductValidator;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductsController {

    private final ProductService service;
    private final ProductValidator validator;
    private final Cart cart;

    @GetMapping
    public Page<ProductDto> getProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "tittle_part", required = false) String tittlePart,
            @RequestParam(name = "min_cost", required = false) Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost
    ) {
        if (page < 1) {
            page = 1;
        }
        return service.find(minCost, maxCost, tittlePart, page).map(MapUtils::toDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return MapUtils.toDto(service.findProductById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        validator.validate(productDto);
        productDto.setId(null);
        Product product = MapUtils.fromDto(productDto);
        service.save(product);
        return MapUtils.toDto(product);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        validator.validate(productDto);
        Product product = service.update(productDto);
        return MapUtils.toDto(product);
    }


    /*
    Предполагаю, что в добавлении и удалении продукта из корзины, использую неправильный подход,
    т.к. по сути мы всё равно каждый раз обращаемся к БД, чтобы достать продукт по ID, и такая корзина ничем не отличается от
    хранения в базе.

    Наверное, в таком случае нужно в теле запроса нужно принимать объект целиком, чтобы проводить с ним манипуляции в корзине,
    но попробовать такой вариант не успел. Ну или поля RequestParam из которых собирается дтошка.
     */
    @GetMapping("/cart/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addToCart(@PathVariable Long id) {
        Product product = service.findProductById(id);
        cart.add(MapUtils.toDto(product));
    }

    @GetMapping("/cart")
    public List<ProductDto> getCart() {
        return cart.getProductList();
    }

    @DeleteMapping("/cart/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFromCart(@PathVariable Long id) {
        Product product = service.findProductById(id);
        cart.remove(MapUtils.toDto(product));
    }
}
