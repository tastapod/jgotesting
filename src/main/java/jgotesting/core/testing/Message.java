package jgotesting.core.testing;

public class Message extends Throwable {
    public Message(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
