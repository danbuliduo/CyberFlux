package io.cyberflux.meta.data;

public enum CyberType {
    EMPTY(0x00, "Empty"),
    CUSTOM(0x01, "Custom"),
    TCP(0x02, "TCP"),
    UDP(0x03, "UDP"),
    HTTP(0x04, "HTTP"),
    COAP(0x05, "CoAP"),
	MQTT(0x07, "MQTT"),
    WEBSOCKET(0x08, "WebSocket"),
	GOSSIP(0x09, "Gossip");

	private int typeFlag;
	private String field;

    private CyberType(int typeFlag, String field) {
		this.typeFlag = typeFlag;
        this.field = field;
    }


	public int getTypeFlag() {
		return this.typeFlag;
	}

    public String getField() {
        return this.field;
    }

    public boolean setField(String field) {
        if(this == CyberType.CUSTOM) {
            this.field = field;
            return true;
        }
        return false;
    }
}