package com.example.webflux.controller;


import com.example.webflux.entity.Person;
import com.example.webflux.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
@Tag(name = "Person API manager", description = "APIs which allow to manage persons")
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public Mono<Person> createPerson(@RequestBody Person person){
        return this.personService.createPerson(person);
    }

    @GetMapping("/id/{id}")
    public Mono<Person> getPersonById(@PathVariable("id") Long id){
        return this.personService.getPersonById(id);
    }

    @GetMapping("/lastname/{lastname}")
    public Flux<Person> getPersonByName(@PathVariable("lastname") String lastName){
        return this.personService.getPersonByName(lastName);
    }

    @GetMapping
    public Flux<Person> getAllPerson(){
        return this.personService.getAllPerson();
    }

    @PutMapping
    public Mono<Person> updatePerson(@RequestBody Person person){
        return this.personService.updatePerson(person);
    }

    @PatchMapping("/id/{id}")
    public Mono<Void> updateNameById(@PathVariable("id") Long id, @RequestParam("lastname") String lastName){
        return this.personService.updateNameById(id,lastName);
    }

    @DeleteMapping("/id/{id}")
    public Mono<Void> deletePersonById(@PathVariable("id") Long id){
        return this.personService.deletePersonById(id);
    }
}
