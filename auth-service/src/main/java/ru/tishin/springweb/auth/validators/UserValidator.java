package ru.tishin.springweb.auth.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tishin.springweb.api.exceptions.ValidationException;
import ru.tishin.springweb.auth.dto.UserDto;
import ru.tishin.springweb.auth.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserService userService;

    public void validate(UserDto userDto) {
        List<String> errors = new ArrayList<>();
        if (userDto.getUsername().isBlank()) {
            errors.add("Имя пользователя не может быть пустым");
        }
        if (userDto.getPassword().length() < 3) {
            errors.add("Пароль должен содержать не менее 3 символов");
        }
        if (userService.existsUsername(userDto.getUsername())) {
            errors.add("Пользователь с именем: " + userDto.getUsername() + " уже существует");
        }
        if (userService.existsEmail(userDto.getEmail())) {
            errors.add("Пользователь с почтой: " + userDto.getEmail() + " уже существует");
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
