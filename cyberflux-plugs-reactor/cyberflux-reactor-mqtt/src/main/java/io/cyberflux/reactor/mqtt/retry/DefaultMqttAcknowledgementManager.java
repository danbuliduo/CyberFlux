package io.cyberflux.reactor.mqtt.retry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.netty.util.HashedWheelTimer;

public class DefaultMqttAcknowledgementManager extends HashedWheelTimer implements MqttAcknowledgementManager {
	private final Map<Long, MqttAcknowledgement> ackMap;

	public DefaultMqttAcknowledgementManager(long tickDuration, TimeUnit unit, int ticksPerWheel) {
		super(tickDuration, unit, ticksPerWheel);
		ackMap = new ConcurrentHashMap<>();
	}

	@Override
	public MqttAcknowledgement getAck(long id) {
		return ackMap.get(id);
	}

	@Override
	public void putAck(MqttAcknowledgement acknowledgement) {
		ackMap.put(acknowledgement.id(), acknowledgement);
	}

	@Override
	public void delAck(long id) {
		ackMap.remove(id);
	}
}
