package io.cyberflux.engine.actuator.prometheus;

import io.cyberflux.engine.actuator.MetricBean;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class PrometheusMetricBean implements MetricBean {

    private final JvmGcMetrics jvmGcMetrics;
    private final PrometheusMeterRegistry meterRegistry;


    public PrometheusMetricBean() {
        this(PrometheusConfig.DEFAULT);
    }

    public PrometheusMetricBean(PrometheusConfig config) {
        meterRegistry = new PrometheusMeterRegistry(config);
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
    };
}
