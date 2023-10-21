package io.cyberflux.engine.actuator.prometheus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrometheusConfig {
    private boolean enable;
}
