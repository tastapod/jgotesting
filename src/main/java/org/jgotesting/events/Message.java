package org.jgotesting.events;

public class Message extends Event {
    public Message(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
