package io.cyberflux.reactor.mqtt;

import io.cyberflux.meta.reactor.ReactorOption;
import io.cyberflux.reactor.mqtt.exception.MqttReactorTypeException;

public class MqttReactorOption extends ReactorOption {
    public static final String[] SUPPORT_TYPES = {"tcp", "ssl", "ws", "wss"};
    protected String type;
    protected String path;

    public MqttReactorOption() {
        this.type = "tcp";
        this.path = "/mqtt";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        for(String i : SUPPORT_TYPES) {
            if(i.equals(type)) {
                this.type = type;
                return;
            }
        }
        throw new MqttReactorTypeException("This type is not supported.");
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static MqttReactorOption defaultOption() {
        return new MqttReactorOption();
    }

}
