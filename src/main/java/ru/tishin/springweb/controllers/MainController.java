package ru.tishin.springweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    // GET [http://localhost:8189/app]/add?a=5&b=10
    @GetMapping("/add")
    @ResponseBody
    public int add(@RequestParam int a, @RequestParam int b) {
        return a + b;
    }

    // GET [http://localhost:8189/app]/product/5503/info
    @GetMapping("/product/{id}/info")
    @ResponseBody
    public String showProductInfo(@PathVariable Long id) {
        return "Product #" + id;
    }

    @GetMapping("/catalog")
    public String showCatalog() {
        return "catalog_page";
    }
}
