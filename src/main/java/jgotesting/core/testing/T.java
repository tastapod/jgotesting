package jgotesting.core.testing;

public class T {
    /** Error is equivalent to Log followed by Fail */
    public void error(Object ...args) {

    }

    /** Errorf is equivalent to Logf followed by Fail */
    public void errorf(String fmt, Object... args) {

    }

    /** Fail marks the function as having failed but continues execution */
    public void fail() {

    }

    /**  FailNow marks the function as having failed and stops its execution */
    public void failNow() {

    }

    /**  Failed reports whether the function has failed */
    public boolean failed() {
        return false;
    }

    /** Log formats its arguments using default formatting, analogous to Println */
    public void log(Object ...args) {

    }

    /** Logf formats its arguments using default formatting, analogous to Printf */
    public void logf(String format, Object ...args) {

    }
}
