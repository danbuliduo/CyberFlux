package io.cyberflux.engine.actuator.influxdb;

import io.cyberflux.engine.actuator.MetricBean;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;


public class InfluxdbMetricBean implements MetricBean {

    private final JvmGcMetrics jvmGcMetrics;
    private final InfluxMeterRegistry meterRegistry;

    public InfluxdbMetricBean() {
        this(InfluxConfig.DEFAULT);
    }

    public InfluxdbMetricBean(InfluxdbConfig config) {
        this(InfluxdbConfig.convert(config));
    }

    public InfluxdbMetricBean(InfluxConfig config) {
        meterRegistry = InfluxMeterRegistry.builder(config).clock(Clock.SYSTEM).build();
        Metrics.globalRegistry.add(meterRegistry);
        jvmGcMetrics = new JvmGcMetrics();
        jvmGcMetrics.bindTo(Metrics.globalRegistry);
        new ClassLoaderMetrics().bindTo(Metrics.globalRegistry);
        new JvmMemoryMetrics().bindTo(Metrics.globalRegistry);
        new ProcessorMetrics().bindTo(Metrics.globalRegistry);
        new JvmThreadMetrics().bindTo(Metrics.globalRegistry);
    }

    public MetricBean close() {
        meterRegistry.close();
        jvmGcMetrics.close();
        return this;
    };

    public MeterRegistry registry() {
        return meterRegistry;
    }
}
