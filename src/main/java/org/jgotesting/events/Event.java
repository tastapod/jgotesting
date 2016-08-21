package org.jgotesting.events;

public abstract class Event extends RuntimeException {

    protected abstract String prefix();

    public Event(Throwable cause) {
        super(cause);
    }

    public Event(String message) {
        super(message);
    }

    public Event(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        String msg = getLocalizedMessage();

        if (msg == null || msg.equals("")) {
            return prefix();
        } else {
            return String.format("%s: %s", prefix(), msg);
        }
    }
}
