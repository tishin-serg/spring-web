package ru.tishin.springweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tishin.springweb.dto.Cart;
import ru.tishin.springweb.dto.StringResponse;
import ru.tishin.springweb.services.CartService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{uuid}")
    public Cart getCurrentCart(@PathVariable String uuid, Principal principal) {
        String username = checkUsername(principal);
        return cartService.getCurrentCart(getCurrentCartUuid(uuid, username));
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void addProduct(@PathVariable String uuid, @PathVariable Long productId, Principal principal) {
        String username = checkUsername(principal);
        cartService.addProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/increase/{productId}")
    public void increaseProduct(@PathVariable String uuid, @PathVariable Long productId, Principal principal) {
        String username = checkUsername(principal);
        cartService.increaseProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void removeProduct(@PathVariable String uuid, @PathVariable Long productId, Principal principal) {
        String username = checkUsername(principal);
        cartService.removeProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/decrease/{productId}")
    public void decreaseProduct(@PathVariable String uuid, @PathVariable Long productId, Principal principal) {
        String username = checkUsername(principal);
        cartService.decreaseProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(@PathVariable String uuid, Principal principal) {
        String username = checkUsername(principal);
        cartService.clearCart(getCurrentCartUuid(uuid, username));
    }

    @GetMapping("/{uuid}/merge")
    public void mergeCart(@PathVariable String uuid, Principal principal) {
        String username = checkUsername(principal);
        cartService.merge(getCurrentCartUuid(null, username), getCurrentCartUuid(uuid, null));
    }

    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    public String getCurrentCartUuid(String uuid, String username) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }

    public String checkUsername(Principal principal) {
        String username = null;
        if (principal != null && principal.getName() != null) {
            username = principal.getName();
        }
        return username;
    }
}
