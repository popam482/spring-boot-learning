package com.popam.learning_spring.web;

public class BookHasNoAuthor extends RuntimeException {
    public BookHasNoAuthor() {
        super("The book has no author");
    }
}
