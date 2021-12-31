package ru.tishin.springweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.dto.Cart;
import ru.tishin.springweb.entities.Product;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addProductById(Long productId) {
        if (!getCurrentCart().addProduct(productId)) {
            Product product = productService.findProductById(productId);
            getCurrentCart().addProduct(product);
        }
    }

    public void clear() {
        getCurrentCart().clear();
    }
}
