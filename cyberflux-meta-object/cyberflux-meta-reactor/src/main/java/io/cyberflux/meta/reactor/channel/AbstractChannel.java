package io.cyberflux.meta.reactor.channel;

import io.cyberflux.meta.data.CyberObject;
import io.cyberflux.meta.data.CyberType;

public abstract class AbstractChannel extends CyberObject implements CyberChannel {

	protected String channelId;
	protected boolean online;

	public AbstractChannel(String channelId) {
        this(CyberType.EMPTY, channelId);
    }

	public AbstractChannel(CyberType type, String channelId) {
		super(type);
		this.channelId = channelId;
	}

	@Override
	public String channelId() {
		return this.channelId;
	}

	public void setOnlineFlag(boolean value) {
		this.online = value;
	}

	@Override
	public boolean isOnline() {
		return online;
	}
}
