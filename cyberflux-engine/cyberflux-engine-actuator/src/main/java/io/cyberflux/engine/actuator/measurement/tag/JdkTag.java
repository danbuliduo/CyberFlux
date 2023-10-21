package io.cyberflux.engine.actuator.measurement.tag;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JdkTag {
    private String home;
    private String version;
}
