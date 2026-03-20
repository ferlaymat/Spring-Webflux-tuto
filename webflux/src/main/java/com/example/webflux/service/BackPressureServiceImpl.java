package com.example.webflux.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BackPressureServiceImpl implements BackPressureService{
    /**
     * Here we simulate state where producer provides more elements
     * than the consumer can proceed.
     */
    @Override
    public Flux<Long> createOverflow(){
        //standard case - provoke a crash
        return Flux.interval(Duration.ofMillis(1))
                .log()
                //concatMap waits for an inner to complete before generating new one.
                //as we produce 1 inner each 1ms, but it takes 100ms to be consumed
                //we provoke an overload
                .concatMap(x->Mono.delay(Duration.ofMillis(100)));
    }
    @Override
    public Flux<Long> createOverflowDrop(){
        return Flux.interval(Duration.ofMillis(1))
                .log()
                //drop all next objects until the consumer is able to consume new object.
                //There is no crash but elements dropped are definitively lost.
                .onBackpressureDrop()
                .concatMap(x->Mono.delay(Duration.ofMillis(100)).thenReturn(x))
                .doOnNext( a -> System.out.println("keep by kept by consumer" + a ));
    }
    @Override
    public Flux<Long> createOverflowBuffer(){
        return Flux.interval(Duration.ofMillis(1))
                .log()
                //buffered 50 elements max then crash like standard behavior
                //Good compromise if the back pressure is limited in time.
                .onBackpressureBuffer(50)
                .concatMap(x->Mono.delay(Duration.ofMillis(100)).thenReturn(x))
                .doOnNext( x -> System.out.println("keep by kept by consumer" + x ));
    }
}
