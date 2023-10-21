package io.cyberflux.engine.actuator.measurement;

import io.cyberflux.engine.actuator.measurement.tag.CpuTag;
import io.cyberflux.engine.actuator.measurement.tag.DiskTag;
import io.cyberflux.engine.actuator.measurement.tag.MemTag;
import io.cyberflux.engine.actuator.measurement.tag.NetTag;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SysMeasurement {
    protected String name;
    protected String arch;
    protected String version;
    protected int process;
    protected int thread;
    protected int handle;
    protected CpuTag cpu;
    protected MemTag mem;
    protected NetTag net;
    protected DiskTag disk;
}
