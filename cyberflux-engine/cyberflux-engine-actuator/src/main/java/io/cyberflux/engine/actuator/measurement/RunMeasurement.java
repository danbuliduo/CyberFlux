package io.cyberflux.engine.actuator.measurement;

import io.cyberflux.engine.actuator.measurement.tag.JdkTag;
import io.cyberflux.engine.actuator.measurement.tag.JvmTag;
import io.cyberflux.engine.actuator.measurement.tag.ThreadTag;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RunMeasurement {
    protected long pid;
    protected long start;
    protected JvmTag jvm;
    protected JdkTag jdk;
    protected Thread thread;

    @Data
    public static class Thread {
        private int count;
        private ThreadTag[] list;
        public Thread(int count) {
            this.count = count;
            this.list = new ThreadTag[count];
        }
    }
}
