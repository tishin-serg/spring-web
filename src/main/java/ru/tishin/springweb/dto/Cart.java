package ru.tishin.springweb.dto;

import lombok.Data;
import ru.tishin.springweb.entities.Product;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<OrderItemDto> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (addProduct(product.getId())) return;
        items.add(new OrderItemDto(product));
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
        for (OrderItemDto o : items) {
            if (o.getProductId().equals(id)) {
                o.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void decreaseProduct(Long id) {
        for (OrderItemDto o : items) {
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

    private void recalculate() {
        totalPrice = 0;
        for (OrderItemDto o : items) {
            totalPrice += o.getPrice();
        }
    }
}
