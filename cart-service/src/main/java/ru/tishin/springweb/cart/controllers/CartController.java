package ru.tishin.springweb.cart.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tishin.springweb.api.cart.CartDto;
import ru.tishin.springweb.api.dto.ProductServiceAppError;
import ru.tishin.springweb.api.dto.StringResponse;
import ru.tishin.springweb.cart.converters.CartConverter;
import ru.tishin.springweb.cart.services.CartService;

@Tag(name = "Контроллер корзин", description = "Отвечает за работу с корзинами")
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/{uuid}")
    @Operation(
            summary = "Запрос получение текущей корзины из редиса по идентификатору",
            description = "Если у пользователя есть корзина, то он её получит по запросу. Если нет, то получит новую",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    )
            })
    public CartDto getCurrentCart(@RequestHeader(required = false) String username,
                                  @PathVariable @Parameter(description = "Идентификатор корзины") String uuid) {
        return cartConverter.entityToDto(cartService.getCurrentCart(getCurrentCartUuid(uuid, username)));
    }

    @GetMapping("/{uuid}/add/{productId}")
    @Operation(
            summary = "Запрос на добавление в корзину продукта по идентификатору",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProductServiceAppError.class))
                    )
            })
    public void addProduct(@RequestHeader(required = false) String username,
                           @PathVariable @Parameter(description = "Идентификатор корзины") String uuid,
                           @PathVariable @Parameter(description = "Идентификатор продукта") Long productId) {
        cartService.addProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/increase/{productId}")
    @Operation(summary = "Запрос на увеличение количества продукта в корзине по идентификатору")
    public void increaseProduct(@RequestHeader(required = false) String username,
                                @PathVariable @Parameter(description = "Идентификатор корзины") String uuid,
                                @PathVariable @Parameter(description = "Идентификатор продукта") Long productId) {
        cartService.increaseProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    @Operation(summary = "Запрос на удаление продукта из корзины по идентификатору")
    public void removeProduct(@RequestHeader(required = false) String username,
                              @PathVariable @Parameter(description = "Идентификатор корзины") String uuid,
                              @PathVariable @Parameter(description = "Идентификатор продукта") Long productId) {
        cartService.removeProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/decrease/{productId}")
    @Operation(summary = "Запрос на уменьшение количества продукта в корзине по идентификатору")
    public void decreaseProduct(@RequestHeader(required = false) String username,
                                @PathVariable @Parameter(description = "Идентификатор корзины") String uuid,
                                @PathVariable @Parameter(description = "Идентификатор продукта") Long productId) {
        cartService.decreaseProduct(getCurrentCartUuid(uuid, username), productId);
    }

    @GetMapping("/{uuid}/clear")
    @Operation(summary = "Запрос на очистку корзины")
    public void clearCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(uuid, username));
    }

    @GetMapping("/{uuid}/merge")
    @Operation(summary = "Запрос на слияние гостевой и пользовательской корзины")
    public void mergeCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.merge(getCurrentCartUuid(null, username), getCurrentCartUuid(uuid, null));
    }

    @GetMapping("/generate")
    @Operation(summary = "Запрос на генерацию идентификатора корзины",
            responses = {
                    @ApiResponse(
                            description = "Идентификатор сгенерирован", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/uuid/{username}")
    @Operation(summary = "Запрос на генерацию идентификатора корзины на основе имени пользователя",
            responses = {
                    @ApiResponse(
                            description = "Идентификатор сгенерирован", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    public String getCartUuidFromUsername(@PathVariable String username) {
        return cartService.getCartUuidFromSuffix(username);
    }


    public String getCurrentCartUuid(String uuid, String username) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }

    @GetMapping("/fill")
    @Operation(summary = "Генерация нескольких корзин со случайно наполненными продуктами. Для тестов")
    public void fillCart() {
        cartService.fillCart();
    }
}
