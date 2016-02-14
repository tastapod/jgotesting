package org.jgotesting;

import org.jgotesting.results.Fail;
import org.jgotesting.results.Message;
import org.junit.Assert;
import org.junit.runners.model.MultipleFailureException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class T {
    private static final ThreadLocal<T> instance = new ThreadLocal<T>();

    private final List<Throwable> errors = new ArrayList<Throwable>();
    private boolean failed = false;

    /**
     * Manage access to the ThreadLocal instance
     */
    protected T() {

    }

    public static T create() {
        T t = new T();
        instance.set(t);
        return t;
    }

    public static void destroy() {
        instance.set(null);
    }

    public static T get() {
        final T t = instance.get();
        assert t != null : "Annotate your test class with @RunWith(JGoTesting.class)";
        return t;
    }

    /** formats its arguments using default formatting, analogous to println */
    public void log(Object ...args) {
        errors.add(trimStackTrace(new Message(join(args))));
    }

    /** Logf formats its arguments using default formatting, analogous to Printf */
    public void logf(String format, Object ...args) {
        log(String.format(format, args));
    }

    /** marks the function as having failed but continues execution */
    public void fail(Object ...args) {
        addError(join(args));
    }

    /** equivalent to logf followed by fail */
    public void failf(String fmt, Object... args) {
        fail(String.format(fmt, args));
    }

    /** stores error from elsewhere verbatim */
    public void failWithException(Throwable t) {
        addError(t);
    }

    /**  marks the function as having failed and stops its execution */
    public void failNow(Object ...args) throws MultipleFailureException {
        fail(args);
        terminate();
    }

    /** equivalent to logf followed by failNow */
    public void failfNow(String fmt, Object... args) throws MultipleFailureException {
        failf(fmt, args);
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
        final List<String> filteredClasses = Arrays.asList(
                T.class.getName(),
                Testing.class.getName(),
                Assert.class.getName());

        final List<StackTraceElement> result = new ArrayList<StackTraceElement>();

        for (StackTraceElement element : t.getStackTrace()) {
            if (!filteredClasses.contains(element.getClassName())) {
                result.add(element);
            }
        }
        t.setStackTrace(result.toArray(new StackTraceElement[result.size()]));
        return t;
    }

    private void addError(String message) {
        addError(new Fail(message));
    }

    private void addError(Throwable e) {
        errors.add(trimStackTrace(e));
        failed = true;
    }

    /**
     * Ugly Java 7 way to do args.join(" ")
     */
    private String join(Object[] args) {
        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            result.append(String.valueOf(arg));
            if (i < args.length - 1) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    private void terminate() throws MultipleFailureException {
        throw new MultipleFailureException(errors);
    }
}
