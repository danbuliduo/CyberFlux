package io.cyberflux.engine.core.config;

import java.util.List;

import io.cyberflux.reactor.mqtt.MqttReactorOption;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReactorConfig {
    protected List<MqttReactorOption> mqttOptions;
}