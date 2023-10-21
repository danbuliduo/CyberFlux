package io.cyberflux.meta.lang;

public enum MetaType {
    EMPTY(0x00, "Empty"),
    TCP(0x02, "TCP"),
    UDP(0x03, "UDP"),
    HTTP(0x04, "HTTP"),
    COAP(0x05, "CoAP"),
	MQTT(0x07, "MQTT"),
    WEBSOCKET(0x08, "WebSocket");

	private int value;
	private String field;

    private MetaType(int value, String field) {
		this.value = value;
        this.field = field;
    }
    public int value() {
        return value;
    }
    public String field() {
        return field;
    }
}