package ru.tishin.springweb.validators;

import org.springframework.stereotype.Component;
import ru.tishin.springweb.dto.ProductDto;
import ru.tishin.springweb.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {

    public void validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        if (productDto.getCost() < 1) {
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
