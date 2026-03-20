package com.example.mongo.controller;

import com.example.mongo.service.BackPressureService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("bp")
public class BackPressureController {
    private BackPressureService backPressureService;

    public BackPressureController(BackPressureService backPressureService) {
        this.backPressureService = backPressureService;
    }

    @GetMapping("/crash")
    public Flux<Long> overflow(){
        return backPressureService.createOverflow();
    }

    @GetMapping(value = "/drop", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> overflowDrop(){
        return backPressureService.createOverflowDrop();
    }

    @GetMapping(value = "/buffer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> overflowBuffer(){
        return backPressureService.createOverflowBuffer();
    }
}
