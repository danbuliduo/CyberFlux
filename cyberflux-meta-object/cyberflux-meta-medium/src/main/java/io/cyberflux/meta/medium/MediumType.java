package io.cyberflux.meta.medium;

public enum MediumType {
    EMPTY("Empty"),
    CUSTOM("Custom"),
    TCP("Transmission Control Protocol"),
    UDP("User Datagram Protocol"),
    HTTP("Hyper Text Transfer Protocol"),
    COAP("Constrained Application Protocol"),
    MQTT("Message Queuing Telemetry Transport"),
    WEBSOCKET("WebSocket");

    private String field;

    MediumType(String field) {
        this.field = field;
    }

    public String getField() {
        return this.field;
    }

    public boolean setField(String field) {
        if(this == MediumType.CUSTOM) {
            this.field = field;
            return true;
        }
        return false;
    }
}