package io.cyberflux.engine.actuator.measurement.tag;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JvmTag {
    private String name;
    private String vendor;
    private String total;
    private String free;
    private String user;
    private String max;
    private float usage;
}
