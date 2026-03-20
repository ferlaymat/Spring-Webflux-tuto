package com.example.webflux.controller;


import com.example.webflux.service.BackPressureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/backpressure")
@RequiredArgsConstructor
public class BackPressureController {
    private final BackPressureService backPressureService;



    @GetMapping("/crash")
    public Flux<Long> overflow(){
        return backPressureService.createOverflow();
    }

    //close the call to end it
    @GetMapping(value = "/drop", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> overflowDrop(){
        return backPressureService.createOverflowDrop();
    }

    @GetMapping(value = "/buffer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> overflowBuffer(){
        return backPressureService.createOverflowBuffer();
    }
}
