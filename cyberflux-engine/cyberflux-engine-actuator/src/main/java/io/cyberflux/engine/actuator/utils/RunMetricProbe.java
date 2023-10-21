package io.cyberflux.engine.actuator.utils;

import io.cyberflux.engine.actuator.MetricProbe;
import io.cyberflux.engine.actuator.measurement.RunMeasurement;
import io.cyberflux.engine.actuator.measurement.tag.JdkTag;
import io.cyberflux.engine.actuator.measurement.tag.JvmTag;
import io.cyberflux.engine.actuator.measurement.tag.ThreadTag;
import oshi.util.FormatUtil;

import java.lang.management.*;
import java.util.Arrays;

public final class RunMetricProbe implements MetricProbe<RunMeasurement> {

    public RunMetricProbe() {}

    @Override
    public RunMeasurement getMeasurement() {
        Runtime runtime = Runtime.getRuntime();
        ThreadMXBean threadMXB = ManagementFactory.getThreadMXBean();
        RuntimeMXBean runtimeMXB = ManagementFactory.getRuntimeMXBean();
        RunMeasurement measurement = new RunMeasurement();
        JdkTag jdk = collectJdkTag();
        JvmTag jvm = collectJvmTag(runtime, runtimeMXB);
        RunMeasurement.Thread thread = collectThread(threadMXB);
        measurement.setPid(runtimeMXB.getPid());
        measurement.setStart(runtimeMXB.getStartTime());
        measurement.setJdk(jdk);
        measurement.setJvm(jvm);
        measurement.setThread(thread);
        return measurement;
    }

    public JdkTag collectJdkTag() {
        JdkTag tag = new JdkTag();
        tag.setHome(System.getProperty("java.home"));
        tag.setVersion(System.getProperty("java.version"));
        return tag;
    }
    public JvmTag collectJvmTag(Runtime runtime, RuntimeMXBean bean) {
        JvmTag tag = new JvmTag();
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long max = runtime.maxMemory();
        long user = total - free;
        tag.setName(bean.getVmName());
        tag.setVendor(bean.getVmVendor());
        tag.setTotal(FormatUtil.formatBytes(total));
        tag.setFree(FormatUtil.formatBytes(free));
        tag.setMax(FormatUtil.formatBytes(max));
        tag.setUser(FormatUtil.formatBytes(user));
        tag.setUsage(user * 1f / total);
        return tag;
    }
    public RunMeasurement.Thread collectThread(ThreadMXBean bean) {
        RunMeasurement.Thread thread = new RunMeasurement.Thread(bean.getThreadCount());
        ThreadTag[] tags = thread.getList();
        int i = 0;
        for(long id: bean.getAllThreadIds()) {
            ThreadInfo info = bean.getThreadInfo(id);
            ThreadTag tag = new ThreadTag();
            tag.setId(id);
            tag.setPrio(info.getPriority());
            tag.setName(info.getThreadName());
            tag.setState(info.getThreadState());
            tags[i++] = tag;
        }
        return thread;
    }
}
