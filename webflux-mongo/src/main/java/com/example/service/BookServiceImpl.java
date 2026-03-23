package com.example.service;

import com.example.entity.Book;
import com.example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    //Non-blocking implementation
    private final BookRepository bookRepository;

    @Override
    public Mono<Book> createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Flux<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> updateBookTitle(String id, String title) {
        return bookRepository.findById(id).flatMap(b -> {
            b.setTitle(title);
            return bookRepository.save(b);
        });
    }

    @Override
    public Mono<Book> updateBookAuthor(String id, String author) {
        return bookRepository.findById(id).flatMap(b -> {
            b.setAuthor(author);
            return bookRepository.save(b);
        });
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
