package io.cyberflux.example.spring.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cyberflux.example.spring.webflux.custom.CustomReactor;
import io.cyberflux.node.engine.core.CyberFluxNodeEngine;
import io.cyberflux.reactor.mqtt.CyberFluxMqttReactor;
import jakarta.annotation.Resource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class ReactorController {
    @Resource
    private CustomReactor server;

    @Resource
    private CyberFluxMqttReactor mqttReactor;

    @Autowired
    private CyberFluxNodeEngine springEngine;

    @GetMapping("/reactor/info/all")
    public Flux<String> info_all() {
        return springEngine.findReactor().map(r -> r.uuid());
    }
    
    @GetMapping("/nodename")
    public Mono<String> nodename() {
        return Mono.just(springEngine.nodeName());
    }
}
