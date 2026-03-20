package com.example.webflux.controller;

import com.example.webflux.service.ProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = ProducerController.class)
public class ProducerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ProducerService producerService;

    @Test
    public void getHello() {
        Mono<String> res = Mono.just("hello");
        when(producerService.getHello()).thenReturn(res);
        Mono<String> response = webTestClient.get().uri("/api/v1/producer").exchange().expectStatus().isOk().returnResult(String.class).getResponseBody().next();
        StepVerifier.create(response).expectSubscription().expectNext("hello").verifyComplete();
    }

    @Test
    public void getGangFomIterable() {
        Flux<String> res = Flux.fromIterable(List.of("G1", "G2", "G3", "GG"));
        when(producerService.getGangFomIterable()).thenReturn(res);
        Flux<String> response = webTestClient.get().uri("/api/v1/producer/gang").exchange().expectStatus().isOk().returnResult(String.class).getResponseBody();
        StepVerifier.create(response).expectSubscription().expectNext("G1").expectNext("G2").expectNext("G3").expectNext("GG").verifyComplete();
    }


}
