package ru.tishin.springweb.services.utils;

import org.springframework.stereotype.Service;
import ru.tishin.springweb.dto.ProductDto;
import ru.tishin.springweb.entities.Product;

@Service
public class MapUtils {
    public static Product fromDto(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTittle(), productDto.getCost());
    }

    public static ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getTittle(), product.getCost());
    }

}
