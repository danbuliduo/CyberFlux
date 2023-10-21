package io.cyberflux.engine.actuator;

public interface MetricProbe<T> {
    T getMeasurement();
}
