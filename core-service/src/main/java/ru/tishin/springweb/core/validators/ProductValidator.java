package ru.tishin.springweb.core.validators;

import org.springframework.stereotype.Component;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.api.exceptions.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {

    public void validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        if (productDto.getCost().compareTo(BigDecimal.ONE) < 0) {
            errors.add("Цена продукта не может быть меньше 1");
        }
        if (productDto.getTittle().isBlank()) {
            errors.add("Название продукта не может быть пустым");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
