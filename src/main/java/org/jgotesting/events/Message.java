package org.jgotesting.events;

public class Message extends Event {
    public Message(String message) {
        super(message);
        setStackTrace(new StackTraceElement[0]);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
