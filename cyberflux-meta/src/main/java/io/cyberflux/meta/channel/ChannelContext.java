package io.cyberflux.meta.channel;

import io.cyberflux.meta.lang.MetaAttribute;

public interface ChannelContext<CHANNEL extends Channel> extends MetaAttribute {
    ChannelGroup<CHANNEL> channelGroup();
}
