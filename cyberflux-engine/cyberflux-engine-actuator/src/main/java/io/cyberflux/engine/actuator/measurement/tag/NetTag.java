package io.cyberflux.engine.actuator.measurement.tag;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NetTag {
    private String hostname;
    private String address;
}
