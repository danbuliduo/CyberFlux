package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberObject;
import io.cyberflux.meta.data.CyberType;

public abstract class TemplateChannel extends CyberObject implements CyberChannel {

	protected String channelId;
	protected boolean online;

	public TemplateChannel(String channelId) {
        this(CyberType.EMPTY, channelId);
    }

	public TemplateChannel(CyberType type, String channelId) {
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
