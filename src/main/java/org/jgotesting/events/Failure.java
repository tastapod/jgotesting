package org.jgotesting.events;

public class Failure extends Event {
    public Failure() {
        super("");
    }
    
    public Failure(String message) {
        super(message);
    }

    public Failure(Throwable cause) {
        super(cause);
    }

    public Failure(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    protected String prefix() {
        return "Check failed";
    }
}
