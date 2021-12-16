package ru.tishin.springweb.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tishin.springweb.dto.ProductDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Cart {
    private List<ProductDto> productList;

    @PostConstruct
    private void init() {
        productList = new ArrayList<>();
    }

    public void add(ProductDto productDto) {
        if (productList.contains(productDto)) {
            ProductDto p = productList.get(getProductList().indexOf(productDto));
            p.setCount(p.getCount() + 1);
        } else {
            productDto.setCount(1);
            productList.add(productDto);
        }
    }

    public void remove(ProductDto productDto) {
        ProductDto p = new ProductDto();
        if (productList.contains(productDto)) {
            p = productList.get(getProductList().indexOf(productDto));
            p.setCount(p.getCount() - 1);
        }
        if (p.getCount() == 0) {
            productList.remove(p);
        }
    }
}
