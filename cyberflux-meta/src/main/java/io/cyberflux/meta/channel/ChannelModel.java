package io.cyberflux.meta.channel;

import java.io.Serializable;

import io.cyberflux.meta.lang.MetaObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ChannelModel extends MetaObject implements Serializable {
    protected String address;
    protected String clientId;
    protected String username;
    protected boolean online;
    protected long keepAlive;
    protected long connectTime;
    protected long disconnectTime;
}
