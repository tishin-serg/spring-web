package ru.tishin.springweb.exceptions;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ValidationException extends RuntimeException {
    private List<String> errors;

    public ValidationException(List<String> errors) {
        super(errors.stream().collect(Collectors.joining(", ")));
        this.errors = errors;
    }
}
