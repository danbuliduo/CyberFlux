package io.cyberflux.engine.actuator.measurement;

import lombok.Data;

@Data
public class TimeMeasurement {
    protected long time;
    public TimeMeasurement() {
        this.time = System.currentTimeMillis();
    }
}
