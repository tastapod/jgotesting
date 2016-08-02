package org.jgotesting.events;

public class Event extends RuntimeException {

    public Event(Throwable cause) {
        super(cause);
    }

    public Event(String message) {
        super(message);
    }

    public Event(String message, Throwable cause) {
        super(message, cause);
    }
}
