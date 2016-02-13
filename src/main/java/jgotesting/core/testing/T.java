package jgotesting.core.testing;

import org.junit.runners.model.MultipleFailureException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class T {
    private final List<Throwable> errors = new ArrayList<Throwable>();
    private boolean failed = false;

    private void addError(String message) {
        final Throwable here = new Throwable();
        StackTraceElement[] stackTrace = here.getStackTrace();
        here.setStackTrace(Arrays.copyOfRange(stackTrace, 2, stackTrace.length));
        errors.add(here);
        failed = true;
    }

    /** marks the function as having failed but continues execution */
    public void fail() {
        addError(null);
    }

    /**  marks the function as having failed and stops its execution */
    public void failNow() throws MultipleFailureException {
        addError(null);
        throw new MultipleFailureException(errors);
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

    public List<Throwable> getErrors() {
        return errors;
    }

    // TODO (maybe) skip, skipNow, skipf, skipped
}
