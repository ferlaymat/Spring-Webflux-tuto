package com.example.webflux.service;

import com.example.webflux.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProducerService {

    Mono<String> getHello();
    Flux<String> getGangFomIterable();
    Flux<Person> getMembers();
    Flux<Person> getMembersFlat();
    Flux<String> getFilteredName();
}
