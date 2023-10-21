package io.cyberflux.engine.actuator.utils;


import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.cyberflux.engine.actuator.MetricProbe;
import io.cyberflux.engine.actuator.measurement.SysMeasurement;
import io.cyberflux.engine.actuator.measurement.tag.CpuTag;
import io.cyberflux.engine.actuator.measurement.tag.DiskTag;
import io.cyberflux.engine.actuator.measurement.tag.MemTag;

import io.cyberflux.engine.actuator.measurement.tag.NetTag;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class SysMetricProbe implements MetricProbe<SysMeasurement> {
    private final static Logger log = LoggerFactory.getLogger(SysMetricProbe.class);
    private final ExecutorService executorService;
    private final SystemInfo systemInfo;

    public SysMetricProbe() {
        executorService = Executors.newFixedThreadPool(2);
        systemInfo = new SystemInfo();
    }

    @Override
    public SysMeasurement getMeasurement() {

        OperatingSystem os = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        SysMeasurement measurement = new SysMeasurement();
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(
           () -> collectCpuTag(hal.getProcessor()), executorService
        ).thenAccept(measurement::setCpu);

        MemTag mem = collectMemTag(hal.getMemory());
        NetTag net = collectNetTag();
        DiskTag disk = collectDiskTag(os);
        measurement.setName(os.getFamily());
        measurement.setArch(System.getProperty("os.arch"));
        measurement.setVersion(os.getVersionInfo().getVersion());
        measurement.setProcess(os.getProcessCount());
        measurement.setThread(os.getThreadCount());
        measurement.setMem(mem);
        measurement.setNet(net);
        measurement.setDisk(disk);
        future.join();
        return measurement;
    }

    public NetTag collectNetTag() {
        NetTag tag = new NetTag();
        try {
            tag.setHostname(InetAddress.getLocalHost().getHostName());
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = netInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address.isSiteLocalAddress()) {
                        tag.setAddress(address.getHostAddress());
                        return tag;
                    }
                }
            }
        } catch(SocketException | UnknownHostException e) {
            log.error(e.getMessage());
        }
        return tag;
    }

    public CpuTag collectCpuTag(CentralProcessor processor) {
        CpuTag tag = new CpuTag();
        // 基本信息
        tag.setName(processor.getProcessorIdentifier().getName());
        tag.setNumber(processor.getPhysicalPackageCount());
        tag.setCore(processor.getPhysicalProcessorCount());
        tag.setLogic(processor.getLogicalProcessorCount());
        // 占用信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(999);
        long[] nextTicks = processor.getSystemCpuLoadTicks();
        long idle = nextTicks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long total = 0;
        for(int i = 0; i < prevTicks.length; i++){
            total += nextTicks[i] - prevTicks[i];
        }
        if (total > 0) {
            tag.setUsage(1f * (total - idle) / total);
            return tag;
        }
        tag.setUsage(0f);
        return tag;
    }

    public MemTag collectMemTag(GlobalMemory memory) {
        MemTag tag = new MemTag();
        MemTag.Global global = new MemTag.Global();
        MemTag.Virtual virtual = new MemTag.Virtual();
        long total = memory.getTotal();
        long available = memory.getAvailable();
        long used = total - available;
        float usage = 0f;
        if (total > 0 && used > 0) {
            usage = used * 1f / total;
        }
        global.setTotal(FormatUtil.formatBytes(total));
        global.setAvailable(FormatUtil.formatBytes(available));
        global.setUsed(FormatUtil.formatBytes(used));
        global.setUsage(usage);

        VirtualMemory virtualMemory = memory.getVirtualMemory();
        long max = virtualMemory.getVirtualMax();
        long use = virtualMemory.getVirtualInUse();
        usage = 0f;
        if (total > 0 && used > 0) {
            usage = use * 1f / max;
        }
        virtual.setMax(FormatUtil.formatBytes(max));
        virtual.setUse(FormatUtil.formatBytes(use));
        virtual.setUsage(usage);

        tag.setGlobal(global);
        tag.setVirtual(virtual);
        return tag;
    }


    public DiskTag collectDiskTag(OperatingSystem os) {
        DiskTag tag = new DiskTag();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        String osName = System.getProperty("os.name");
        long available = 0, total = 0;
        if (osName.toLowerCase().startsWith("win")) {
            for (OSFileStore fs : fsArray) {
                available += fs.getUsableSpace();
                total += fs.getTotalSpace();
            }
        } else {
            Set<String> names = new HashSet<>(fsArray.size());
            for (OSFileStore fs : fsArray) {
                if (names.add(fs.getName())) {
                    available = fs.getUsableSpace();
                    total = fs.getTotalSpace();
                }
            }
        }
        long used = total - available;
        tag.setTotal(FormatUtil.formatBytes(total));
        tag.setAvailable(FormatUtil.formatBytes(available));
        tag.setUsed(FormatUtil.formatBytes(used));
        float usageRate = 0f;
        if (total > 0 && used > 0) {
            usageRate = used * 1f / total;
        }
        tag.setUsage(usageRate);
        return tag;
    }
}
