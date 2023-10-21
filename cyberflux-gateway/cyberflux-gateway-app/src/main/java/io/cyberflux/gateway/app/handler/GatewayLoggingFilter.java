package io.cyberflux.gateway.app.handler;


import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import io.cyberflux.gateway.app.context.GatewaySendEventContext;
import io.cyberflux.gateway.app.codec.LoggerMessage;
import org.springframework.http.codec.ServerSentEvent;

public class GatewayLoggingFilter extends Filter<LoggingEvent> {
    private final GatewaySendEventContext sendEventContext;
    public GatewayLoggingFilter() {
        sendEventContext = GatewaySendEventContext.instance();
    }

    @Override
    public FilterReply decide(LoggingEvent event) {
        LoggerMessage message = LoggerMessage.fromLoggingEvent(event);
        sendEventContext.emit(ServerSentEvent.builder(message).event("log-update").build());
        return  FilterReply.ACCEPT;
    }
}
