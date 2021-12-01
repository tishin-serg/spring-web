package ru.tishin.springweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.entities.Product;
import ru.tishin.springweb.responses.ResponseDelivery;
import ru.tishin.springweb.services.ProductService;

import java.util.List;

@RestController
public class MainController {

    private ProductService service;

    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }

    @GetMapping("/catalog")
    public List<Product> getCatalog() {
        return service.getCatalog();
    }

    @GetMapping("/catalog/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }


    @GetMapping("/catalog/change_cost")
    public void changeCostById(@RequestParam Long productId, @RequestParam Integer delta) {
        service.changeCostById(productId, delta);
    }

    @GetMapping("/catalog/{id}")
    public Product findProductById(@PathVariable Long id) {
        return service.findProductById(id);
    }

    @GetMapping("/catalog/products_cheaper")
    public List<Product> getProductsCheaperThan(@RequestParam Integer max) {
        return service.findAllByCostLessThan(max);
    }

    @GetMapping("/catalog/products_expensive")
    public List<Product> getProductsExpensiveThan(@RequestParam Integer min) {
        return service.findAllByCostMoreThan(min);
    }

    @GetMapping("/catalog/products_between")
    public List<Product> getProductsBetween(@RequestParam Integer min, @RequestParam Integer max) {
        return service.findAllByCostBetween(min, max);
    }

    @PostMapping("/catalog")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        service.addNewProduct(product);
        return new ResponseEntity<>(new ResponseDelivery(HttpStatus.CREATED.value(), "Product was created. " + product.toString()),
                HttpStatus.CREATED);
    }
}
