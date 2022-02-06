package ru.tishin.springweb.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<OrderItemDto> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(ProductDto productDto) {
        if (addProduct(productDto.getId())) return;
        items.add(new OrderItemDto(productDto));
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

    public void merge(Cart anotherCart) {
        for (OrderItemDto anotherItem : anotherCart.items) {
            boolean merged = false;
            for (OrderItemDto userItem : items) {
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
