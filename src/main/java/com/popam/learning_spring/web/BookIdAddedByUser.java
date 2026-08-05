package com.popam.learning_spring.web;

public class BookIdAddedByUser extends RuntimeException{
    public BookIdAddedByUser() {
        super("Users cannot introduce the book's id");
    }


}
