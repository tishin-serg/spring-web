package ru.tishin.springweb.services.utils;

import org.springframework.stereotype.Service;
import ru.tishin.springweb.dto.ProductDto;
import ru.tishin.springweb.entities.Product;

@Service
public class MapUtils {
    public static Product mapProductDtoToProduct(ProductDto productDto) {
        return new Product(productDto);
    }

    public static ProductDto mapProductToProductDto(Product product) {
        return new ProductDto(product);
    }


}
