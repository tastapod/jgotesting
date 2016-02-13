package org.jgotesting.results;

public class Fail extends RuntimeException {
    public Fail(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Fail" + (getMessage() != null ? ": " + getMessage() : "");
    }
}
