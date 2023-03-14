package io.cyberflux.meta.reactor;

import java.time.Instant;

public class ClusterMessage {
    private Object payload;
    private Instant timeStamp = Instant.now();

    public ClusterMessage(Object payload) {
        this.payload = payload;
    }
    public Instant getTimeStamp() {
        return timeStamp;
    }
    public Object getPayload() {
        return payload;
    }

    public static ClusterMessage from(Object payload) {
        return new ClusterMessage(payload);
    }

    public static class MessageBuilder {

    }
}
