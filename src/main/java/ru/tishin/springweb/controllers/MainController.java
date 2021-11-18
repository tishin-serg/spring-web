package ru.tishin.springweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tishin.springweb.model.Product;
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

    @GetMapping("/catalog/delete_product")
    public void deleteById(@RequestParam Long productId) {
        service.deleteById(productId);
    }


    @GetMapping("/catalog/change_cost")
    public void changeCostById(@RequestParam Long productId, @RequestParam Integer delta) {
        service.changeCostById(productId, delta);
    }
}
