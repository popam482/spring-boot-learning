package com.popam.learning_spring.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {
    private List<Book> bookList = new ArrayList<>(List.of(
            new Book(1, "Clean Code", "Robert Martin", 2008),
            new Book(2, "Effective Java", "Joshua Bloch", 2001)
    ));

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookList;
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Integer id) {
        return bookList.stream().filter(book -> book.getId().equals(id)).findFirst().orElseThrow(()-> new BookNotFoundException(id));
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleBookNotFoundException(BookNotFoundException e) {
        return e.getMessage();
    }
}
