package io.cyberflux.gateway.app.codec;

import org.springframework.util.StringUtils;

import io.cyberflux.engine.core.model.EngineModel;
import io.scalecube.cluster.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EngineEntity extends EngineModel {
	private static String UNKNOWN = "UNKNOWN";
	private static String UNDEFINED = "UNDEFINED";

	protected long start;
	protected Status status;

	public static EngineEntity fromClusterMember(Member member) {
		EngineEntity option = new EngineEntity();
		option.setId(member.id());
		option.setHost(member.address().toString());
		option.setName(StringUtils.hasText(member.alias()) ? member.alias() : UNDEFINED);
		option.setMode(UNDEFINED);
		return option;
	}

	public static EngineEntity fromOnlyHost(String host) {
		EngineEntity option = new EngineEntity();
		option.setId(UNKNOWN);
		option.setHost(host);
		option.setMode(UNKNOWN);
		option.setName(UNKNOWN);
		option.setVersion(UNKNOWN);
		option.setStatus(Status.UNKNOWN);
		return option;
	}

	public static enum Status {
		DOWN,
		UP,
		RESTRICTED,
		UNKNOWN,
		OUT_OF_SERVICE,
		OFFLINE
	}
}
