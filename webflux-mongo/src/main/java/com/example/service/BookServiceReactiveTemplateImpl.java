package com.example.service;


import com.example.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BookServiceReactiveTemplateImpl implements BookService{

    //Non-blocking implementation
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Book> createBook(Book book) {
        return mongoTemplate.save(book);
    }

    @Override
    public Mono<Book> getBookById(String id) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = Query.query(criteria);
        return mongoTemplate.findOne(query, Book.class);
    }

    @Override
    public Flux<Book> getAllBook() {
        return mongoTemplate.findAll(Book.class);
    }

    @Override
    public Mono<Book> updateBookTitle(String id, String title) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = Query.query(criteria);
        Update update =new Update();
        update.set("title", title);
        return mongoTemplate.findAndModify(query,update, Book.class);
    }

    @Override
    public Mono<Book> updateBookAuthor(String id, String author) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = Query.query(criteria);
        Update update =new Update();
        update.set("author", author);
        return mongoTemplate.findAndModify(query,update, Book.class);
    }

    @Override
    public void deleteBook(String id) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = Query.query(criteria);
        mongoTemplate.remove(query, Book.class).then();
    }
}
