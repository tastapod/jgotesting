package jgotesting.core.testing;

import jgotesting.core.testing.results.Fail;
import jgotesting.core.testing.results.Message;
import org.junit.runners.model.MultipleFailureException;

import java.util.ArrayList;
import java.util.Arrays;
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
        terminate();
    }

    /** formats its arguments using default formatting, analogous to println */
    public void log(Object ...args) {
        errors.add(trimStackTrace(new Message(buildMessage(args))));
    }

    /** Logf formats its arguments using default formatting, analogous to Printf */
    public void logf(String format, Object ...args) {
        log(String.format(format, args));
    }

    /** equivalent to log followed by fail */
    public void error(Object ...args) {
        addError(buildMessage(args));
    }

    /** equivalent to logf followed by fail */
    public void errorf(String fmt, Object... args) {
        error(String.format(fmt, args));
    }

    /** equivalent to Log followed by FailNow */
    public void fatal(Object ...args) throws MultipleFailureException {
        error(args);
        terminate();
    }

    /** equivalent to logf followed by failNow */
    public void fatalf(String fmt, Object... args) throws MultipleFailureException {
        errorf(fmt, args);
        terminate();
    }

    /**  Failed reports whether the function has failed */
    public boolean failed() {
        return failed;
    }

    // TODO (maybe) skip, skipNow, skipf, skipped

    public List<Throwable> getErrors() {
        return errors;
    }

    /**
     * Remove references to ourselves from a stack trace
     *
     * @param t throwable whose stack trace we mutate
     */
    private Throwable trimStackTrace(Throwable t) {
        final List<String> filteredClasses = Arrays.asList(T.class.getName(), Testing.class.getName());

        final List<StackTraceElement> result = new ArrayList<StackTraceElement>();

        for (StackTraceElement element : t.getStackTrace()) {
            if (!filteredClasses.contains(element.getClassName())) {
                result.add(element);
            }
        }
        t.setStackTrace(result.toArray(new StackTraceElement[result.size()]));
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

    private void terminate() throws MultipleFailureException {
        throw new MultipleFailureException(errors);
    }
}
