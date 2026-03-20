package com.example.webflux.service;

import reactor.core.publisher.Flux;

public interface BackPressureService {

    Flux<Long> createOverflow();
    Flux<Long> createOverflowDrop();
    Flux<Long> createOverflowBuffer();
}
