package io.cyberflux.engine.spring.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import io.cyberflux.engine.actuator.ActuatorConfig;
import io.cyberflux.engine.actuator.ActuatorAgent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class EngineActuatorService implements DisposableBean, Runnable {

    private Map<String, Object> currentMeasurement;
    private volatile boolean enable;

    private final ActuatorAgent agent;
    private final Sinks.Many<Map<String, Object>> sinks;

    public EngineActuatorService(ActuatorConfig config) {
        this(new ActuatorAgent(config));
    }

    public EngineActuatorService(ActuatorAgent agent) {
        this.currentMeasurement = new ConcurrentHashMap<>();
        this.agent = agent;
        this.enable = agent.getConfig().isEnable();
        this.sinks = Sinks.many().multicast().onBackpressureBuffer();
        this.sinks.asFlux().subscribe(item -> currentMeasurement = item);
        Thread thread = new Thread(this);
        thread.start();
    }

    public Flux<Map<String, Object>> measurementFlux() {
        return sinks.asFlux();
    }

    public Mono<Map<String, Object>> measurementLast() {
        return Mono.just(this.currentMeasurement);
    }

    @Override
    public void run() {
        while(this.enable) {
            sinks.tryEmitNext(agent.measure());
        }
    }

    @Override
    public void destroy() {
        this.enable = false;
    }
}
