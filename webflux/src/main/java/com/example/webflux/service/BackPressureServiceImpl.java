package com.example.mongo.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BackPressureServiceImpl implements BackPressureService{

    public Flux<Long> createOverflow(){
        return Flux.interval(Duration.ofMillis(1))
                .log()
                .concatMap(x->Mono.delay(Duration.ofMillis(100)));
    }

    public Flux<Long> createOverflowDrop(){
        return Flux.interval(Duration.ofMillis(1))
                .log()
                .onBackpressureDrop()
                .concatMap(x->Mono.delay(Duration.ofMillis(100)).thenReturn(x))
                .doOnNext( a -> System.out.println("keep by kept by consumer" + a ));
    }

    public Flux<Long> createOverflowBuffer(){
        return Flux.interval(Duration.ofMillis(1))
                .log()
                .onBackpressureBuffer(50)
                .concatMap(x->Mono.delay(Duration.ofMillis(100)).thenReturn(x))
                .doOnNext( x -> System.out.println("keep by kept by consumer" + x ));
    }
}
