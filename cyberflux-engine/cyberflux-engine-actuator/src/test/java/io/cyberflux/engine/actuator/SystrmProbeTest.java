package io.cyberflux.engine.actuator;

import org.junit.jupiter.api.Test;

import io.cyberflux.engine.actuator.measurement.SysMeasurement;
import io.cyberflux.engine.actuator.utils.SysMetricProbe;

public class SystrmProbeTest {
    @Test
    public void run() {
        SysMetricProbe probe = new SysMetricProbe();
        SysMeasurement measurement = probe.getMeasurement();
        System.out.println(measurement);
    }
}
