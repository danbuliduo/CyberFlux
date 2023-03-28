package io.cyberflux.node.engine;

import io.cyberflux.meta.reactor.CyberFluxReactor;
import io.cyberflux.node.engine.huaxu.annotation.CyberBean;
import io.cyberflux.node.engine.huaxu.annotation.CyberConfiguration;
import io.cyberflux.node.engine.huaxu.annotation.CyberParam;
import io.cyberflux.reactor.coap.CyberFluxCoapReactor;
import io.cyberflux.reactor.mqtt.CyberFluxMqttReactor;

@CyberConfiguration
public class TestConfig {

    @CyberBean
    public CyberFluxReactor mqttserver(@CyberParam CustomReactor reactor) {
        return CyberFluxMqttReactor.builder().build();
    }

    @CyberBean
    public CyberFluxReactor coapServer(CustomReactor reactor) {
        return CyberFluxCoapReactor.builder().build();
    }
}
