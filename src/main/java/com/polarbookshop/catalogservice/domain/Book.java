package com.polarbookshop.catalogservice.domain;

public record Book(
        String isbn,
        String title,
        String author,
        Double price
) {
    public static Book of(String isbn, String title, String author, Double price){
        return new Book(isbn, title, author, price);
    }
}
