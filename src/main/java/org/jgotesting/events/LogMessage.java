package org.jgotesting.events;

public class LogMessage extends Event {
    public LogMessage(Throwable cause) {
        this("", cause);
    }

    public LogMessage(String message) {
        this(message, null);
    }

    public LogMessage(String message, Throwable cause) {
        super(message, cause);
        setStackTrace(new StackTraceElement[0]);
    }

    @Override
    protected String prefix() {
        return "Log";
    }
}
