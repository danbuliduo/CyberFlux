package io.cyberflux.engine.actuator;

import org.junit.jupiter.api.Test;

import io.cyberflux.engine.actuator.measurement.RunMeasurement;
import io.cyberflux.engine.actuator.utils.RunMetricProbe;

public class JvmProbeTest {
    @Test
    public void run() {
        RunMetricProbe probe = new RunMetricProbe();
        RunMeasurement measurement = probe.getMeasurement();
        System.out.println(measurement);
    }
}
