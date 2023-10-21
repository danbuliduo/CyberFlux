package io.cyberflux.engine.spring.web;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;

import io.cyberflux.engine.spring.service.EngineActuatorService;
import io.cyberflux.engine.spring.web.annotation.EngineController;
import io.cyberflux.utils.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EngineController("engine/api/actuator")
public class EngineActuatorController {

    private final EngineActuatorService engineActuatorService;

    public EngineActuatorController(EngineActuatorService engineActuatorService) {
        this.engineActuatorService = engineActuatorService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Object>> get() {
        return this.engineActuatorService.measurementLast();
    }

    @GetMapping(path = "/path", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Object> getByPath(@RequestParam("key") String key) {
        TypeReference<Map<String, Object>> reference = new TypeReference<Map<String, Object>>(){};
        return this.engineActuatorService.measurementLast()
            .map(items -> {
                String[] keys = key.split("_");
                int l = keys.length;
                for(int i = 0; i < l -1; i++) {
                    items = ObjectUtils.convertValue(items.get(keys[i]), reference);
                }
                return items.get(keys[l - 1]);
            }).onErrorResume(e -> Mono.empty());
    }
}
