package org.jgotesting.events;

public class FatalFailure extends Failure {
    public FatalFailure() {
        super();
    }

    public FatalFailure(String message) {
        super(message);
    }

    public FatalFailure(Throwable cause) {
        super(cause);
    }

    public FatalFailure(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    protected String prefix() {
        return "Fatal failure";
    }
}
