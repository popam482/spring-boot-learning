package com.popam.learning_spring.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Book {
    private Integer id;
    @NotBlank(message = "The book has no title")
    private String title;
    @NotBlank(message = "The book has no author")
    private String author;
    @NotNull(message = "The book has no published year")
    @Min(value=0, message = "The book has a wrong published year")
    private Integer publishedYear;

    public Book() {
    }

    public Book(Integer id, String title, String author, Integer publishedYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }
}
