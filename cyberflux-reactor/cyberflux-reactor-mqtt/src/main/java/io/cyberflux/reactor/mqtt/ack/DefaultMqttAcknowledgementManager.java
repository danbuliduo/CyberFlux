package io.cyberflux.reactor.mqtt.ack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.netty.util.HashedWheelTimer;

public class DefaultMqttAcknowledgementManager extends HashedWheelTimer implements MqttAcknowledgementManager {
	private final Map<Long, MqttAcknowledgement> acks;

	public DefaultMqttAcknowledgementManager(long tickDuration, TimeUnit unit, int ticksPerWheel) {
		super(tickDuration, unit, ticksPerWheel);
		acks = new ConcurrentHashMap<>();
	}

	@Override
	public MqttAcknowledgement getAck(long id) {
		return acks.get(id);
	}

	@Override
	public void putAck(MqttAcknowledgement acknowledgement) {
		acks.put(acknowledgement.id(), acknowledgement);
	}

	@Override
	public void delAck(long id) {
		acks.remove(id);
	}
}
