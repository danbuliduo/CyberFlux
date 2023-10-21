package io.cyberflux.engine.spring.web;

import io.cyberflux.reactor.mqtt.codec.MqttSubscriptionStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import io.cyberflux.engine.spring.web.annotation.EngineController;
import io.cyberflux.reactor.mqtt.channel.MqttChannelContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EngineController("engine/api/subscription")
public class EngineSubscriptionController {

    public EngineSubscriptionController() {

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Object> get() {
        return Flux.fromIterable(
            MqttChannelContext.instance().subscriptionRegistry().findAll()
        ).map(MqttSubscriptionStore::toSimpleModel);
    }

    @GetMapping(path = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Long> getCount() {
        return Mono.just(MqttChannelContext.instance().subscriptionRegistry().count());
    }
}
