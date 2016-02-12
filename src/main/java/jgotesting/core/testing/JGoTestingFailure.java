package jgotesting.core.testing;

public class JGoTestingFailure extends RuntimeException {
    private final T t;

    public JGoTestingFailure(T t) {
        this.t = t;
    }
}
