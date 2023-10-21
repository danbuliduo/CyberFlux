package io.cyberflux.engine.actuator.measurement.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemTag {

    private Global global;
    private Virtual virtual;

    @Data
    @NoArgsConstructor
    public static class Global {
        private String total;
        private String available;
        private String used;
        private float usage;
    }
    @Data
    @NoArgsConstructor
    public static class Virtual {
        private String max;
        private String use;
        private float usage;
    }
}
