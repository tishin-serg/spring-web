package ru.tishin.springweb.validators;

import org.springframework.stereotype.Component;
import ru.tishin.springweb.dto.ProductDto;
import ru.tishin.springweb.dto.UserDto;
import ru.tishin.springweb.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator {

    public void validate(UserDto userDto) {
        List<String> errors = new ArrayList<>();
        if (userDto.getUsername().isBlank()) {
            errors.add("Имя пользователя не может быть пустым");
        }
        if (userDto.getPassword().length() < 5) {
            errors.add("Пароль должен содержать не менее 5 символов");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

//        if (us.getCost() < 1) {
//            errors.add("Цена продукта не может быть меньше 1");
//        }
//        if (productDto.getTittle().isBlank()) {
//            errors.add("Название продукта не может быть пустым");
//        }
//        if (!errors.isEmpty()) {
//            throw new ValidationException(errors);
//        }
}
