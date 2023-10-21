package io.cyberflux.engine.core.config;

import io.cyberflux.cluster.ClusterOption;
import io.cyberflux.engine.core.exception.EngineModeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClusterConfig extends ClusterOption {

	protected String mode;

	public void setMode(String mode) {
		if("ENGINE".equalsIgnoreCase(mode)) {
			this.mode = "ENGINE";
		} else if("GATEWAY".equalsIgnoreCase(mode)) {
			this.mode = "GATEWAY";
		} else {
			throw new EngineModeException("This mode is not supported.");
		}
	}

	public String getMode() {
		return mode;
	}

}
