package ru.tishin.springweb.core.converters;

import org.springframework.stereotype.Component;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.core.entities.Product;

@Component
public class ProductConverter {
    public Product fromProductDto(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTittle(), productDto.getCost());
    }

    public ProductDto toProductDto(Product product) {
        return new ProductDto(product.getId(), product.getTittle(), product.getCost());
    }
}
