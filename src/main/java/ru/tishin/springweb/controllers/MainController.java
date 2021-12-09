package ru.tishin.springweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.dto.ProductDto;
import ru.tishin.springweb.entities.Product;
import ru.tishin.springweb.responses.ResponseDelivery;
import ru.tishin.springweb.services.ProductService;
import ru.tishin.springweb.services.utils.MapUtils;

@RestController
@RequestMapping("/api/v1/catalog")
public class MainController {

    private ProductService service;

    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public Page<ProductDto> getCatalog(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "tittle_part", required = false) String tittlePart,
            @RequestParam(name = "min_cost", required = false) Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost
    ) {
        if (page < 1) {
            page = 1;
        }
        return service.find(minCost, maxCost, tittlePart, page).map(MapUtils::mapProductToProductDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PatchMapping("/cost")
    public void changeCostById(@RequestParam Long productId, @RequestParam Integer delta) {
        service.changeCostById(productId, delta);
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id) {
        return service.findProductById(id);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
        productDto.setId(null);
        Product product = MapUtils.mapProductDtoToProduct(productDto);
        service.save(product);
        return new ResponseEntity<>(new ResponseDelivery(HttpStatus.CREATED.value(), "Product was created. " + product.toString()),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
        Product product = MapUtils.mapProductDtoToProduct(productDto);
        service.save(product);
        return new ResponseEntity<>(new ResponseDelivery(HttpStatus.OK.value(),
                "Product was updated. " + product.toString()),
                HttpStatus.OK);
    }
}
