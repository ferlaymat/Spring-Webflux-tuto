package com.example.webflux.service;

import com.example.webflux.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface ProducerService {

    Mono<String> getHello();
    Flux<String> getGangFomIterable();
    Flux<Person> getMembers();
    Flux<Person> getMembersFlat();
    Flux<String> getFilteredName();
    Flux<String> toUpperCase();
    Flux<String> toUpperCaseFM();
    Flux<Integer> getFluxConcat();
    Flux<Integer> getFluxMerge();
    Flux<Tuple3<Integer, Integer, Integer>> getFluxZip();
    Flux<List<Integer>> getList();
    Mono<Map<Integer, Integer>> getCollectMap();
    void doEach();
}
