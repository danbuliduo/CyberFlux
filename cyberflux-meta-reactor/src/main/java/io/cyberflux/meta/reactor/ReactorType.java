package io.cyberflux.meta.reactor;

public enum ReactorType {
    EMPTY("Empty"),
    CUSTOM("Custom"),
    TCP("Transmission Control Protocol"),
    UDP("User Datagram Protocol"),
    HTTP("Hyper Text Transfer Protocol"),
    COAP("Constrained Application Protocol"),
    MQTT("Message Queuing Telemetry Transport"),
    WEBSOCKET("WebSocket");

    private String fullName;

    ReactorType(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public boolean setFullName(String fullName) {
        if(this == ReactorType.CUSTOM) {
            this.fullName = fullName;
            return true;
        }
        return false;
    }
}