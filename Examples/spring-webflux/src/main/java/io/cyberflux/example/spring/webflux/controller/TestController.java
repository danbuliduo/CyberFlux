package io.cyberflux.example.spring.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class TestController {
    @Autowired
    private CustomAbstractServer server;

    @GetMapping("/uuid")
    public Mono<String> uuid() {
        return Mono.just(server.uuid());
    }
}
