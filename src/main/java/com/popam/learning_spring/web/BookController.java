package com.popam.learning_spring.web;

import jakarta.validation.Valid;
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

    private void validateBook(Book book) {
        if (book.getTitle() == null || "".equals(book.getTitle())) {
            throw new BookHasNoTitle();
        }
        if (book.getAuthor() == null || "".equals(book.getAuthor())) {
            throw new BookHasNoAuthor();
        }
        if (book.getPublishedYear() == null || book.getPublishedYear() < 0) {
            throw new BookHasWrongPublishedYear(book.getPublishedYear());
        }
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookList;
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Integer id) {
        return bookList.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PostMapping("/books")
    public Book addBook(@Valid @RequestBody Book book) {
        if (book.getId() != null) {
            throw new BookIdAddedByUser();
        }
       //validateBook(book);
       int nextId = bookList.stream()
               .mapToInt(Book::getId)
               .max()
               .orElse(1);
       book.setId(nextId + 1);
       bookList.add(book);
       return book;
    }

    @PatchMapping("/books/{id}")
    public Book patchBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        Book bookToUpdate = bookList.stream()
                .filter(book1 -> book1.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));
        if(updatedBook.getTitle() != null) {
            if(updatedBook.getTitle().isEmpty()) {
                throw new BookHasNoTitle();
            }
            bookToUpdate.setTitle(updatedBook.getTitle());
        }
        if(updatedBook.getAuthor() != null) {
            if(updatedBook.getAuthor().isEmpty()) {
                throw new BookHasNoAuthor();
            }
            bookToUpdate.setAuthor(updatedBook.getAuthor());
        }
        if(updatedBook.getPublishedYear() != null) {
            if(updatedBook.getPublishedYear() < 0) {
                throw new BookHasWrongPublishedYear(updatedBook.getPublishedYear());
            }
            bookToUpdate.setPublishedYear(updatedBook.getPublishedYear());
        }

        return bookToUpdate;
    }

    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        Book bookToUpdate = bookList.stream()
                .filter(book1 -> book1.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));

        //validateBook(updatedBook);

        bookToUpdate.setTitle(updatedBook.getTitle());
        bookToUpdate.setAuthor(updatedBook.getAuthor());
        bookToUpdate.setPublishedYear(updatedBook.getPublishedYear());

        return bookToUpdate;
    }

    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Integer id) {
        Book bookToDelete = bookList.stream()
                .filter(book1 -> book1.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));

        bookList.remove(bookToDelete);
    }
}
