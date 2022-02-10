package ru.tishin.springweb.cart.models;

import lombok.Data;
import ru.tishin.springweb.api.core.ProductDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(ProductDto productDto) {
        if (addProduct(productDto.getId())) return;
        items.add(new CartItem(productDto));
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = 0;
    }

    public void removeProduct(Long id) {
        items.removeIf(i -> i.getProductId().equals(id));
        recalculate();
    }


    public boolean addProduct(Long id) {
        for (CartItem o : items) {
            if (o.getProductId().equals(id)) {
                o.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void decreaseProduct(Long id) {
        for (CartItem o : items) {
            if (o.getProductId().equals(id)) {
                o.changeQuantity(-1);
                if (o.getQuantity() <= 0) {
                    items.remove(o);
                }
                recalculate();
                return;
            }
        }
    }

    protected void recalculate() {
        totalPrice = 0;
        for (CartItem o : items) {
            totalPrice += o.getPrice();
        }
    }

    public void merge(Cart anotherCart) {
        for (CartItem anotherItem : anotherCart.items) {
            boolean merged = false;
            for (CartItem userItem : items) {
                if (anotherItem.getProductId().equals(userItem.getProductId())) {
                    userItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        anotherCart.clear();
    }
}
