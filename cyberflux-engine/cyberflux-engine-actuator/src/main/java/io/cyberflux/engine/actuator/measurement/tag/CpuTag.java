package io.cyberflux.engine.actuator.measurement.tag;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CpuTag {
    private String name;
    private int number;
    private int logic;
    private int core;
    private float usage;
}
