package com.example.webflux.service;

import com.example.webflux.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {

    Mono<Person> createPerson(Person person);
    Mono<Person> getPersonById(Long id);
    Flux<Person> getPersonByName(String lastName);
    Flux<Person> getAllPerson();
    Mono<Person> updatePerson(Person person);
    Mono<Void> updateNameById(Long id, String lastName);
    Mono<Void> deletePersonById(Long id);
}
