package org.jgotesting;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jgotesting.events.Failure;
import org.jgotesting.events.FatalFailure;
import org.jgotesting.events.Message;
import org.junit.runners.model.MultipleFailureException;

import java.util.ArrayList;
import java.util.List;

public class T {
    private static final ThreadLocal<T> instance = new ThreadLocal<T>();

    private final List<Throwable> events = new ArrayList<Throwable>();
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
        if (t == null) {
            throw new RuntimeException("Annotate your test class with @RunWith(JGoTesting.class)");
        }
        return t;
    }

    /**
     * log a message which is only displayed if the test fails
     */
    public void log(Object... args) {
        events.add(new Message(join(args)));
    }

    /**
     * formats its arguments analogous to printf
     */
    public void logf(String format, Object... args) {
        log(String.format(format, args));
    }

    /**
     * marks the function as having failed but continues execution
     */
    public void fail(Object... args) {
        addFailure(join(args));
    }

    /**
     * equivalent to logf followed by fail
     */
    public void failf(String fmt, Object... args) {
        fail(String.format(fmt, args));
    }

    /**
     * marks the function as having failed and stops its execution
     */
    public void failNow(Object... args) throws Exception {
        addFatalError(join(args));
        finish();
    }

    /**
     * equivalent to logf followed by failNow
     */
    public void failfNow(String fmt, Object... args) throws Exception {
        failNow(String.format(fmt, args));
        finish();
    }

    // The Hamcrest matcher methods are static, because generics

    public static <V> void logIf(String reason, V value, Matcher<? super V> matcher) {
        if (matcher.matches(value)) {
            get().log(describeMatch(reason, value, matcher));
        }
    }

    public static <V> void logIf(V value, Matcher<? super V> matcher) {
        logIf("", value, matcher);
    }

    public static <V> void logUnless(String reason, V value, Matcher<? super V> matcher) {
        if (!matcher.matches(value)) {
            get().log(describeMismatch(reason, value, matcher));
        }
    }

    public static <V> void logUnless(V value, Matcher<? super V> matcher) {
        logUnless("", value, matcher);
    }

    public static <V> void failIf(String reason, V value, Matcher<? super V> matcher) {
        if (matcher.matches(value)) {
            get().fail(describeMatch(reason, value, matcher));
        }
    }

    public static <V> void failIf(V value, Matcher<? super V> matcher) {
        failIf("", value, matcher);
    }

    public static <V> void failUnless(String reason, V value, Matcher<? super V> matcher) {
        if (!matcher.matches(value)) {
            get().fail(describeMismatch(reason, value, matcher));
        }
    }

    public static <V> void failUnless(V value, Matcher<? super V> matcher) {
        failUnless("", value, matcher);
    }

    public static <V> void failNowIf(String reason, V value, Matcher<? super V> matcher) throws Exception {
        if (matcher.matches(value)) {
            get().failNow(describeMatch(reason, value, matcher));
        }
    }

    public static <V> void failNowIf(V value, Matcher<? super V> matcher) throws Exception {
        failNowIf("", value, matcher);
    }

    public static <V> void failNowUnless(String reason, V value, Matcher<? super V> matcher) throws Exception {
        if (!matcher.matches(value)) {
            get().failNow(describeMismatch(reason, value, matcher));
        }
    }

    public static <V> void failNowUnless(V value, Matcher<? super V> matcher) throws Exception {
        failNowUnless("", value, matcher);
    }

    static <V> Description describeMatch(String reason, V value, Matcher<? super V> matcher) {
        return new StringDescription()
                .appendText(reason)
                .appendText("\nDidn't want: ")
                .appendDescriptionOf(matcher)
                .appendText("\nbut got:     ")
                .appendValue(value);
    }

    static <V> Description describeMismatch(String reason, V value, Matcher<? super V> matcher) {
        return new StringDescription()
                .appendText(reason)
                .appendText("\nWanted:  ")
                .appendDescriptionOf(matcher)
                .appendText("\nbut got: ")
                .appendValue(value);
    }

    /**
     * throws an exception if anything went wrong during the test
     */
    void finish() throws Exception {
        if (failed) {
            throw new MultipleFailureException(events);
        }
    }

    // TODO (maybe) skip, skipNow, skipf, skipped (override BlockJUnit4ClassRunner#runChild)

    /**
     * Remove references to ourselves from a stack trace
     *
     * @param cause throwable whose stack trace we mutate
     */
    Throwable trimStackTrace(Throwable cause) {
        final String thisPackage = getClass().getPackage().getName();
        final String junitAssertClassName = org.junit.Assert.class.getName();
        final List<StackTraceElement> result = new ArrayList<StackTraceElement>();

        for (StackTraceElement element : cause.getStackTrace()) {
            String className = element.getClassName();
            String packageName = className.substring(0, className.lastIndexOf('.'));

            if (!thisPackage.equals(packageName) && !className.equals(junitAssertClassName)) {
                result.add(element);
            }
        }
        cause.setStackTrace(result.toArray(new StackTraceElement[result.size()]));
        return cause;
    }

    private void addFailure(String message) {
        addFailure(new Failure(message));
    }

    private void addFatalError(String message) {
        addFailure(new FatalFailure(message));
    }

    void addFailure(Throwable cause) {
        events.add(trimStackTrace(cause));
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
}
