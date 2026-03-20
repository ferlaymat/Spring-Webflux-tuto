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
import reactor.util.function.Tuple3;

import java.util.List;
import java.util.Map;

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

    @GetMapping(value = "/upper", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> toUpperCase() {
        return this.producerService.toUpperCase();
    }

    @GetMapping(value = "/upper/flat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> toUpperCaseFM(){
        return this.producerService.toUpperCaseFM();
    }

    @GetMapping(value = "/concat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getFluxConcat(){
        return this.producerService.getFluxConcat();
    }

    @GetMapping(value = "/merge", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getFluxMerge(){
        return this.producerService.getFluxMerge();
    }

    @GetMapping(value = "/zip", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple3<Integer, Integer, Integer>> getFluxZip(){
        return this.producerService.getFluxZip();
    }

    @GetMapping(value = "/list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<Integer>> getList(){
        return this.producerService.getList();
    }

    @GetMapping(value = "/collect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Map<Integer, Integer>> getCollectMap(){
        return this.producerService.getCollectMap();
    }

    @GetMapping(value = "/doeach", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public void doEach(){
        this.producerService.doEach();
    }

}
