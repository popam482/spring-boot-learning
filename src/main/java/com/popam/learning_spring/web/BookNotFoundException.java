package com.popam.learning_spring.web;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Integer id) {
        super("Could not find book with id " + id);
    }
}
