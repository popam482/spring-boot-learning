package com.popam.learning_spring.web;

public class BookHasNoTitle extends RuntimeException {
    public BookHasNoTitle() {
        super("The book has no title");
    }
}
