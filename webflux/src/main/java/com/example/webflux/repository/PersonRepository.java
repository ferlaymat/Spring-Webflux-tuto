package com.example.webflux.repository;

import com.example.webflux.entity.Person;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends R2dbcRepository<Person, Long> {

    Flux<Person> findByLastName(String lastName);
}
