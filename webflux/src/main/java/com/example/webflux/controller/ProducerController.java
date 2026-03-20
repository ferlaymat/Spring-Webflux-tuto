package com.example.webflux.controller;

import com.example.webflux.entity.Person;
import com.example.webflux.service.ProducerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/producer")
@RequiredArgsConstructor
@Tag(name = "Producer API manager", description = "APIs which allow to test Flux and Mono methods")
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping
    public Mono<String> getHello(){
        return this.producerService.getHello();
    }

    @GetMapping(value = "/gang", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getGangFomIterable(){
        return this.producerService.getGangFomIterable();
    }

    @GetMapping(value = "/member", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> getMembers(){
        return this.producerService.getMembers();
    }

    @GetMapping(value = "/member/flat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> getMembersFlat(){
        return this.producerService.getMembersFlat();
    }

    @GetMapping(value = "/member/name", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getFilteredName(){
        return this.producerService.getFilteredName();
    }


}
