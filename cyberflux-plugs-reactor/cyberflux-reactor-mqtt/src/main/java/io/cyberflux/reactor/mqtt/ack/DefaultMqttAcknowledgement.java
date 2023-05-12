package io.cyberflux.reactor.mqtt.ack;

import java.util.concurrent.TimeUnit;

import io.netty.util.Timeout;

public class DefaultMqttAcknowledgement implements MqttAcknowledgement {
    private volatile boolean dideFlag;
	private final long id;
	private final int maxRetries;
	private final int period;
	private final Runnable runnable;
    private final Runnable cleaner;
	private final MqttAcknowledgementManager manager;
	private int count;

	public DefaultMqttAcknowledgement(
			long id,
			int maxRetries,
			int period,
			Runnable runnable,
			Runnable cleaner,
			MqttAcknowledgementManager manager) {
		this.id =id;
		this.maxRetries = maxRetries;
		this.period = period;
		this.runnable = runnable;
		this.cleaner = cleaner;
		this.manager = manager;
		this.dideFlag = false;
		this.count = 1;
	}

	@Override
	public void run(Timeout timeout) throws Exception {
		if (++count <= maxRetries + 1 && !dideFlag) {
			try {
				runnable.run();
				manager.putAck(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			cleaner.run();
		}
	}

	@Override
	public long id() {
		return this.id;
	}

	@Override
	public void start() {
		manager.putAck(this);
	}

	@Override
	public void stop() {
		dideFlag = true;
		manager.delAck(this.id());
	}


	@Override
	public int timed() {
		return this.period * this.count;
	}

	@Override
	public TimeUnit timeUnit() {
		return TimeUnit.SECONDS;
	}

}
