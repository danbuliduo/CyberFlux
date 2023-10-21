package io.cyberflux.engine.actuator;

import io.micrometer.core.instrument.MeterRegistry;

public interface MetricBean {
    MetricBean close();
    MeterRegistry registry();
}
