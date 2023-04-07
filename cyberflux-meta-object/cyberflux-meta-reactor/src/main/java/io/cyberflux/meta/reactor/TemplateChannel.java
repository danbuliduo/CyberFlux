package io.cyberflux.meta.reactor;

import io.cyberflux.meta.data.CyberObject;
import io.cyberflux.meta.data.CyberType;

public class TemplateChannel extends CyberObject implements CyberChannel {

	protected String channelId;

	public TemplateChannel(String channelId) {
        this(CyberType.EMPTY, channelId);
    }

	public TemplateChannel(CyberType type, String channelId) {
		super(type);
		this.channelId = channelId;
    }

	public String channelId() {
		return this.channelId;
	}
}
