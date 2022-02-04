package ru.tishin.springweb.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tishin.springweb.dto.OrderDto;
import ru.tishin.springweb.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDtoValidator {

    public void validate(OrderDto orderDto) {
        List<String> errors = new ArrayList<>();
        if (orderDto.getAddress().isBlank()) {
            errors.add("Поле \"адрес\" должно быть заполнено");
        }
        if (orderDto.getAddress().isBlank()) {
            errors.add("Поле \"телефон\" должно быть заполнено");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
