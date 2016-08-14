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

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getName(), getMessage());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().isInstance(obj)) {
            return false;
        } else {
            Event that = (Event) obj;
            return this.getMessage().equals(that.getMessage());
        }
    }

    @Override
    public int hashCode() {
        return getMessage().hashCode();
    }
}
