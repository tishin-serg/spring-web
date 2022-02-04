package ru.tishin.springweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.dto.Cart;
import ru.tishin.springweb.api.dto.StringResponse;
import ru.tishin.springweb.services.CartService;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{uuid}")
    public Cart getCurrentCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        return cartService.getCurrentCart(getCurrentCartUuid(uuid, username));
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void addProduct(@RequestHeader(required = false) String username, @PathVariable String uuid,
                           @PathVariable Long productId) {
        cartService.addProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/increase/{productId}")
    public void increaseProduct(@RequestHeader(required = false) String username, @PathVariable String uuid,
                                @PathVariable Long productId) {
        cartService.increaseProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void removeProduct(@RequestHeader(required = false) String username, @PathVariable String uuid,
                              @PathVariable Long productId) {
        cartService.removeProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/decrease/{productId}")
    public void decreaseProduct(@RequestHeader(required = false) String username, @PathVariable String uuid,
                                @PathVariable Long productId) {
        cartService.decreaseProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(uuid, username));
    }

    @GetMapping("/{uuid}/merge")
    public void mergeCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
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
}
