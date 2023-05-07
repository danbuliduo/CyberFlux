package io.cyberflux.reactor.mqtt.retry;

public interface MqttAcknowledgementManager {
	MqttAcknowledgement getAck(long id);
	void putAck(MqttAcknowledgement acknowledgement);
	void delAck(long id);
}
