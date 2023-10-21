package io.cyberflux.engine.actuator.measurement.tag;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ThreadTag {
    private long id;
    private int prio;
    private String name;
    private Thread.State state;
}
