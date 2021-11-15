package ru.tishin.springweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tishin.springweb.repository.ProductRepository;

@Controller
public class MainController {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // GET [http://localhost:8189/app]/catalog
    @GetMapping("/catalog")
    public String showCatalogPage(Model model) {
        model.addAttribute("catalog", productRepository.getCatalog());
        return "catalog_page";
    }

    @GetMapping("/catalog/{id}")
    public String showProductPage(Model model, @PathVariable Long id) {
        model.addAttribute("product", productRepository.findById(id));
        return "product_info_page";
    }
}
