package jgotesting.core.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class T {
    private final List<Throwable> errors = new ArrayList<Throwable>();
    private boolean failed = false;

    private void addError(String message) {
        final Throwable here = new Throwable();
        errors.add(here);
        failed = true;
    }

    /** marks the function as having failed but continues execution */
    public void fail() {
        addError(null);
    }

    /**  marks the function as having failed and stops its execution */
    public void failNow() {
        fail();
        throw new JGoTestingFailure(this);
    }


    /** Log formats its arguments using default formatting, analogous to Println */
    public void log(Object ...args) {

    }

    /** Logf formats its arguments using default formatting, analogous to Printf */
    public void logf(String format, Object ...args) {

    }

    /** Error is equivalent to Log followed by Fail */
    public void error(Object ...args) {

    }

    /** Errorf is equivalent to Logf followed by Fail */
    public void errorf(String fmt, Object... args) {

    }

    /** Fatal is equivalent to Log followed by FailNow */
    public void fatal(Object ...args) {

    }

    /** Fatalf is equivalent to Logf followed by FailNow */
    public void fatalf(String fmt, Object... args) {

    }

    /**  Failed reports whether the function has failed */
    public boolean failed() {
        return failed;
    }

    public void skipNow() {

    }

    public static class Error {
    }

    // TODO (maybe) skip, skipNow, skipf, skipped
}
