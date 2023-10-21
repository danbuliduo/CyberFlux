package io.cyberflux.engine.actuator;

import io.cyberflux.engine.actuator.influxdb.InfluxdbConfig;
import io.cyberflux.engine.actuator.prometheus.PrometheusConfig;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActuatorConfig {
    private boolean enable = true;
    private InfluxdbConfig influxdb = new InfluxdbConfig();
    private PrometheusConfig prometheus = new PrometheusConfig();
}
