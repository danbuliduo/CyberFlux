package io.cyberflux.meta.reactor;

import java.io.Serializable;

import io.cyberflux.meta.lang.MetaObject;
import io.cyberflux.meta.transport.TransportConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ReactorModel extends MetaObject implements Serializable {
    protected String uuid;
    protected ReactorStatus status;
    protected TransportConfig transport;
}
