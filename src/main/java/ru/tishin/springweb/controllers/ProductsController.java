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
import ru.tishin.springweb.utils.MapUtils;
import ru.tishin.springweb.validators.ProductValidator;

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
        return service.find(minCost, maxCost, tittlePart, page).map(MapUtils::toProductDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return MapUtils.toProductDto(service.findProductById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        validator.validate(productDto);
        productDto.setId(null);
        Product product = MapUtils.fromProductDto(productDto);
        service.save(product);
        return MapUtils.toProductDto(product);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        validator.validate(productDto);
        Product product = service.update(productDto);
        return MapUtils.toProductDto(product);
    }


//    //todo переделать корзину по REST
//    @GetMapping("/cart/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void addToCart(@PathVariable Long id) {
//        Product product = service.findProductById(id);
//        cart.add(MapUtils.toDto(product));
//    }
//
//    @GetMapping("/cart")
//    public List<ProductDto> getCart() {
//        return cart.getProductList();
//    }
//
//    @DeleteMapping("/cart/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void removeFromCart(@PathVariable Long id) {
//        Product product = service.findProductById(id);
//        cart.remove(MapUtils.toDto(product));
//    }
}
