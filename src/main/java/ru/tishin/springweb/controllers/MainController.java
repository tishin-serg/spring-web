package ru.tishin.springweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tishin.springweb.model.Product;
import ru.tishin.springweb.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {
    private ProductService service;

    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }

    // Приходится здесь сортировать лист перед отрисовкой во фронте,
    // потому что при изменении стоимости продукта почему-то возвращается json с другим порядком продуктов
    // Подскажите, каким образом можно здесь обойтись без сортировки?
    // Рассматривал вариант с оптимизацией фронта, чтобы после изменения стоимости обновлялась не таблица,
    // а отдельная строка. Но подумал, что слишком заморочено. Может есть более оптимальное решение
    @GetMapping("/catalog")
    public List<Product> getCatalog() {
        List<Product> productList = service.getCatalog().stream()
                .sorted((o1, o2) -> o1.getId().intValue() - o2.getId().intValue())
                .collect(Collectors.toList());
        return productList;
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
