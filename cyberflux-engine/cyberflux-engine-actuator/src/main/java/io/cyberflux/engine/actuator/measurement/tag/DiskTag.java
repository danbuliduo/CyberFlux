package io.cyberflux.engine.actuator.measurement.tag;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiskTag {
    private String total;
    private String available;
    private String used;
    private float usage;
}
