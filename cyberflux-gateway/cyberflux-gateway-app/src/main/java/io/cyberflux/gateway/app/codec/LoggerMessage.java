package io.cyberflux.gateway.app.codec;

import ch.qos.logback.classic.spi.LoggingEvent;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LoggerMessage {

    private String message;
    private String level;
    private String threadName;
    private long timeStamp;
    public static LoggerMessage fromLoggingEvent(LoggingEvent event) {
        LoggerMessage message = new LoggerMessage();
        message.setMessage(event.getFormattedMessage());
        message.setThreadName(event.getThreadName());
        message.setLevel(event.getLevel().levelStr);
        message.setTimeStamp(event.getTimeStamp());
        return message;
    }
}
