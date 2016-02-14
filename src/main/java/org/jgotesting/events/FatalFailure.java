package org.jgotesting.events;

public class FatalFailure extends Failure {
    public FatalFailure(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Fatal failure";
    }
}
