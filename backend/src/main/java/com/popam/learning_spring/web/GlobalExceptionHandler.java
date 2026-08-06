package com.popam.learning_spring.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleBookNotFoundException(BookNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(BookIdAddedByUser.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBookIdAddedByUser(BookIdAddedByUser e) {
        return e.getMessage();
    }

    @ExceptionHandler(BookHasNoTitle.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBookHasNoTitle(BookHasNoTitle e) {
        return e.getMessage();
    }

    @ExceptionHandler(BookHasNoAuthor.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBookHasNoAuthor(BookHasNoAuthor e) {
        return e.getMessage();
    }

    @ExceptionHandler(BookHasWrongPublishedYear.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBookHasWrongPublishedYear(BookHasWrongPublishedYear e) {
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

}
