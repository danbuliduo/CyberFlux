package io.cyberflux.engine.actuator;

import java.util.HashMap;
import java.util.Map;

import io.cyberflux.engine.actuator.influxdb.InfluxdbConfig;
import io.cyberflux.engine.actuator.influxdb.InfluxdbMetricBean;
import io.cyberflux.engine.actuator.measurement.RunMeasurement;
import io.cyberflux.engine.actuator.measurement.SysMeasurement;
import io.cyberflux.engine.actuator.prometheus.PrometheusConfig;
import io.cyberflux.engine.actuator.utils.RunMetricProbe;
import io.cyberflux.engine.actuator.utils.SysMetricProbe;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.influx.InfluxMeterRegistry;
import lombok.Data;

@Data
public class ActuatorAgent {

    private final ActuatorConfig config;
    private final RunMetricProbe runProbe;
    private final SysMetricProbe sysProbe;

    public ActuatorAgent(ActuatorConfig config) {
        this.config = config;
        this.runProbe = new RunMetricProbe();
        this.sysProbe = new SysMetricProbe();
        InfluxdbConfig influxdb = config.getInfluxdb();
        PrometheusConfig prometheus = config.getPrometheus();
        if(influxdb.isEnable()) {
            InfluxdbMetricBean bean = new InfluxdbMetricBean(influxdb);
        }
    }

    public Map<String, Object> measure() {
        return new HashMap<>() {{
            put("time", System.currentTimeMillis());
            put("run", measureRun());
            put("sys", measureSys());
        }};
    }

    public RunMeasurement measureRun() {
        return runProbe.getMeasurement();
    }

    public SysMeasurement measureSys() {
        return sysProbe.getMeasurement();
    }
}
