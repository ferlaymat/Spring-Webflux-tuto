package com.example.webflux.controller;

import com.example.webflux.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private PersonController personController;

    @Test
    public void createPerson() {
        Person input = new Person(null, "Bruce", "Wayne");
        Person output = new Person(1L, "Bruce", "Wayne");
        when(personController.createPerson(input)).thenReturn(Mono.just(output));
        Mono<Person> response = webTestClient.post().uri("/api/v1/person").body(Mono.just(input), Person.class).exchange().expectStatus().isCreated().returnResult(Person.class).getResponseBody().next();
        StepVerifier.create(response).expectSubscription().expectNext(output).verifyComplete();
    }


}
