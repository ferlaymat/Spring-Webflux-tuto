package com.example.webflux.service;

import com.example.webflux.entity.Person;
import com.example.webflux.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProducerServiceImpl implements ProducerService {
    private final PersonRepository personRepository;
    private final List<String> gangs = List.of("Maelstrom", "Valentinos", "6th Street", "The Voodoo Boys", "Animals", "Tyger Claws", "Moxes");
    private final List<String> members = List.of("Brick", "Welles", "Buchanan", "Placide", "Sasquatch", "T-Bug", "Judy");

    @Override
    public Mono<String> getHello() {
        //use JustOrEmty() in case the content is null.
        //Just is no able to manage null value
        return Mono.just("Hellooo NightCity");
    }

    @Override
    public Flux<String> getGangFomIterable() {
        //convert an iterable to a Flux
        return Flux.fromIterable(gangs);
    }

    /**
     * Synchronous treatment - wait 1 sec for each element
     */
    @Override
    public Flux<Person> getMembers() {
        return personRepository.findAll()
                .map(person -> {
                    log.info("MAP - Processing: {} on {}", person.getFirstName(), Thread.currentThread().getName());
                    person.setFirstName(person.getFirstName().toUpperCase());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return person;
                });
    }

    /**
     * Parallelized treatment - wait 1 sec for each element
     */
    @Override
    public Flux<Person> getMembersFlat() {
        return personRepository.findAll()
                .flatMap(person -> {
                    log.info("FLATMAP - Processing: {} on {}", person.getFirstName(), Thread.currentThread().getName());
                    person.setFirstName(person.getFirstName().toUpperCase());
                    return Mono.just(person).delayElement(Duration.ofMillis(1000));
                });
    }

    @Override
    public Flux<String> getFilteredName() {
        return Flux.fromIterable(members).filter(n -> n.length() < 6);
    }
}
