package org.jgotesting.events;

public class Failure extends Event {
    public Failure(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Check failed";
    }
}
