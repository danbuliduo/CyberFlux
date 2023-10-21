package io.cyberflux.engine.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EngineRegisterMessage {
    private String activationCode;
    private EngineModel engine;
}
