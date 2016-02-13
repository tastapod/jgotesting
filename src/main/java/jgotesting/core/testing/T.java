package jgotesting.core.testing;

import org.junit.runners.model.MultipleFailureException;

import java.util.ArrayList;
import java.util.List;

public class T {
    private final List<Throwable> errors = new ArrayList<Throwable>();
    private boolean failed = false;

    private void addError(String message) {
        errors.add(trimStackTrace(new Fail(message)));
        failed = true;
    }

    /** marks the function as having failed but continues execution */
    public void fail() {
        addError(null);
    }

    /**  marks the function as having failed and stops its execution */
    public void failNow() throws MultipleFailureException {
        fail();
        throw new MultipleFailureException(errors);
    }

    /** formats its arguments using default formatting, analogous to println */
    public void log(Object ...args) {
        errors.add(trimStackTrace(new Message(buildMessage(args))));
    }

    /** Logf formats its arguments using default formatting, analogous to Printf */
    public void logf(String format, Object ...args) {
        log(String.format(format, args));
    }

    /** Error is equivalent to Log followed by Fail */
    public void error(Object ...args) {
        addError(buildMessage(args));
    }

    /** Errorf is equivalent to Logf followed by Fail */
    public void errorf(String fmt, Object... args) {
        error(String.format(fmt, args));
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

    /**
     * Remove references to ourselves from a stack trace
     *
     * @param t throwable whose stack trace we mutate
     */
    private Throwable trimStackTrace(Throwable t) {
        final List<StackTraceElement> stack = new ArrayList<StackTraceElement>();
        for (StackTraceElement element : t.getStackTrace()) {
            if (!element.getClassName().equals(this.getClass().getName())) {
                stack.add(element);
            }
        }
        t.setStackTrace(stack.toArray(new StackTraceElement[stack.size()]));
        return t;
    }

    /**
     * Ugly Java 7 way to do args.join(" ")
     */
    private String buildMessage(Object[] args) {
        final StringBuilder out = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            out.append(String.valueOf(arg));
            if (i < args.length - 1) {
                out.append(" ");
            }
        }
        return out.toString();
    }


    // TODO (maybe) skip, skipNow, skipf, skipped
}
