package io.cyberflux.node.engine;

import io.cyberflux.meta.reactor.CyberReactor;
import io.cyberflux.node.engine.huaxu.annotation.CyberBean;
import io.cyberflux.node.engine.huaxu.annotation.CyberConfiguration;
import io.cyberflux.node.engine.huaxu.annotation.CyberParam;
import io.cyberflux.reactor.coap.CoapReactor;
import io.cyberflux.reactor.mqtt.MqttReactor;
import io.cyberflux.reactor.mqtt.transport.TcpTransportConfig;

@CyberConfiguration
public class TestConfig {

    @CyberBean
    public CyberReactor mqttserver(@CyberParam CustomReactor reactor) {
        return MqttReactor.builder()
			.config("/test.yaml", TcpTransportConfig.class)
			.build();
    }

    @CyberBean
    public CoapReactor coapServer(CustomReactor reactor) {
        return CoapReactor.builder().build();
    }
}
