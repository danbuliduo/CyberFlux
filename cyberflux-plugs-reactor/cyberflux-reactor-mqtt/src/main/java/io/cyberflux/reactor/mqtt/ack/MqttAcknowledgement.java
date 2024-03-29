package io.cyberflux.reactor.mqtt.ack;

import java.util.concurrent.TimeUnit;

import io.netty.util.TimerTask;

public interface MqttAcknowledgement extends TimerTask {
	TimeUnit timeUnit();
	int timed();
	long id();
	void start();
	void stop();
}
