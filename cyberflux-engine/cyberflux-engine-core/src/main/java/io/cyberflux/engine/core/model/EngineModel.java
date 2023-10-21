package io.cyberflux.engine.core.model;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EngineModel implements Serializable {
	protected String id;
	protected String host;
	protected String mode;
	protected String name;
	protected String version;
	protected String apiPath;
}
