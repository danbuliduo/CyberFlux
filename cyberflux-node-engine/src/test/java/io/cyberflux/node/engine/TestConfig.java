package io.cyberflux.node.engine;

import io.cyberflux.meta.reactor.ReactiveServer;
import io.cyberflux.node.engine.annotation.CyberBean;
import io.cyberflux.node.engine.annotation.CyberReactor;
import io.cyberflux.reactor.mqtt.MqttReactor;

@CyberReactor
public class TestConfig {
    @CyberBean
    public ReactiveServer mqttserver() {
        return MqttReactor.builder().build();
    }
}
