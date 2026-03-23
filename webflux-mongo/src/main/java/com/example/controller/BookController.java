package com.example.controller;

import com.example.entity.Book;
import com.example.service.BookServiceImpl;
import com.example.service.BookServiceReactiveTemplateImpl;
import com.example.service.BookServiceTemplateImpl;
import com.example.type.MethodType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookServiceImpl bookServiceImpl;
    private final BookServiceTemplateImpl bookServiceTemplate;
    private final BookServiceReactiveTemplateImpl bookServiceReactiveTemplate;

    @PostMapping
    public Mono<Book> createBook(@RequestBody Book book, @RequestParam(value = "method") MethodType method){
        return switch (method){
            case TEMPLATE -> bookServiceTemplate.createBook(book);
            case REACTIVE_TEMPLATE -> bookServiceReactiveTemplate.createBook(book);
            case REPOSITORY -> bookServiceImpl.createBook(book);
        };
    }

    @GetMapping("/id/{id}")
    public Mono<Book> getBookById(@PathVariable("id") String id, @RequestParam(value = "method") MethodType method){
        return switch (method){
            case TEMPLATE -> bookServiceTemplate.getBookById(id);
            case REACTIVE_TEMPLATE -> bookServiceReactiveTemplate.getBookById(id);
            case REPOSITORY -> bookServiceImpl.getBookById(id);
        };
    }

    @GetMapping
    public Flux<Book> getAllBook(@RequestParam("method") MethodType method){
        return switch (method){
            case TEMPLATE -> bookServiceTemplate.getAllBook();
            case REACTIVE_TEMPLATE -> bookServiceReactiveTemplate.getAllBook();
            case REPOSITORY -> bookServiceImpl.getAllBook();
        };
    }

    @PatchMapping("/title/id/{id}")
    public Mono<Book> updateBookTitle(@PathVariable("id") String id, @RequestParam("title") String title, @RequestParam(value = "method") MethodType method){
        return switch (method){
            case TEMPLATE -> bookServiceTemplate.updateBookTitle(id,title);
            case REACTIVE_TEMPLATE -> bookServiceReactiveTemplate.updateBookTitle(id,title);
            case REPOSITORY -> bookServiceImpl.updateBookTitle(id,title);
        };
    }

    @PatchMapping("/author/id/{id}")
    public Mono<Book> updateBookAuthor(@PathVariable("id") String id, @RequestParam("author") String author, @RequestParam(value = "method") MethodType method){
        return switch (method){
            case TEMPLATE -> bookServiceTemplate.updateBookAuthor(id,author);
            case REACTIVE_TEMPLATE -> bookServiceReactiveTemplate.updateBookAuthor(id,author);
            case REPOSITORY -> bookServiceImpl.updateBookAuthor(id,author);
        };
    }

    @DeleteMapping
    public void deleteBook(@PathVariable("id") String id, @RequestParam(value = "method") MethodType method){
        switch (method){
            case TEMPLATE -> bookServiceTemplate.deleteBook(id);
            case REACTIVE_TEMPLATE -> bookServiceReactiveTemplate.deleteBook(id);
            case REPOSITORY -> bookServiceImpl.deleteBook(id);
        };
    }
}
