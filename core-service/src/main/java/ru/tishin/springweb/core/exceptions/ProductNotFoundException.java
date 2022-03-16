package ru.tishin.springweb.core.exceptions;

import ru.tishin.springweb.api.exceptions.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
