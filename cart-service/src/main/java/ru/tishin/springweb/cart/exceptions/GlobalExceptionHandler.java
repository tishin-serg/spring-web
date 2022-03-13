package ru.tishin.springweb.cart.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tishin.springweb.api.exceptions.AppError;
import ru.tishin.springweb.api.exceptions.IntegrationException;
import ru.tishin.springweb.api.exceptions.ResourceNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler
//    public ResponseEntity<AppError> catchHttpTimeoutException(HttpTimeoutException e) {
//        log.error(e.getMessage(), e);
//        return new ResponseEntity<>(new AppError(HttpStatus.REQUEST_TIMEOUT.value(), e.getMessage()), HttpStatus.REQUEST_TIMEOUT);
//    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchWebClientResponseException(IntegrationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }


}