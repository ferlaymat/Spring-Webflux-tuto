package com.example.webflux.service;

import com.example.webflux.entity.Person;
import com.example.webflux.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;
import static org.springframework.data.relational.core.query.Update.update;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<Person> createPerson(Person person) {
        return this.personRepository.save(person).onErrorMap(e -> new IllegalStateException(String.format("Error: not able to save: %s, ex: %s", person,e)));
    }

    @Override
    public Mono<Person> getPersonById(Long id) {
        return this.personRepository.findById(id).onErrorMap(e -> new IllegalStateException(String.format("Error: this id: %s not exists, ex: %s", id,e)));
    }

    @Override
    public Flux<Person> getPersonByName(String lastName) {
        return this.personRepository.findByLastName(lastName);
    }

    @Override
    public Flux<Person> getAllPerson() {
        return this.personRepository.findAll();
    }

    @Override
    public Mono<Person> updatePerson(Person person) {
        return this.personRepository.save(person).onErrorMap(e -> new IllegalStateException(String.format("Error: not able to update: %s, ex: %s", person,e)));
    }

    @Override
    public Mono<Void> updateNameById(Long id, String lastName) {
        return this.template
                .update(Person.class)
                .matching(query(where("id").is(id)))
                .apply(update("lastName", lastName))
                .then()
                .onErrorMap(e -> new IllegalStateException(String.format("Error: not able to update entity: %s, ex: %s", id,e)));
        //replace then() by this and replace return type by Mono<Person>
        //but this change will execute a second query after the update
       /* .flatMap(rowsUpdated -> template.selectOne(
                query(where("id").is(id)),
                Person.class
        ));*/
    }

    @Override
    public Mono<Void> deletePersonById(Long id) {
        return this.personRepository.deleteById(id).onErrorMap(e -> new IllegalStateException(String.format("Error: not able to delete entity: %s, ex: %s", id,e)));
    }
}
