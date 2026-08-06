package com.popam.learning_spring.web;

public class BookHasWrongPublishedYear extends RuntimeException {
    public BookHasWrongPublishedYear(Integer publishedYear) {
        String message;
        if(publishedYear == null){
            message = "The book has no published year";
        }
        else{
            message = "The book has a wrong published year";
        }
        super(message);
    }
}
