package io.cyberflux.meta.channel;

import io.cyberflux.meta.lang.MetaType;

public abstract class AbstractChannel extends ChannelModel implements Channel {
    public AbstractChannel(MetaType type) {
       this.type = type;
    }
    public AbstractChannel(MetaType type, String clientId) {
        this.type = type;
        this.clientId = clientId;
    }
}
