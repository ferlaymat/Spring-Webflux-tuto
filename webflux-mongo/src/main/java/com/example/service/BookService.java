package com.example.service;

import com.example.entity.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Mono<Book> createBook(Book book);
    Mono<Book> getBookById(String id);
    Flux<Book> getAllBook();
    Mono<Book> updateBookTitle(String id,String title);
    Mono<Book> updateBookAuthor(String id, String author);
    void deleteBook(String id);
}
