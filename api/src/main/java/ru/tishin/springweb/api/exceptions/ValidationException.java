package ru.tishin.springweb.api.exceptions;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


public class ValidationException extends RuntimeException {
    private List<String> errors;

    public ValidationException(List<String> errors) {
        super(errors.stream().collect(Collectors.joining(", ")));
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }


}
