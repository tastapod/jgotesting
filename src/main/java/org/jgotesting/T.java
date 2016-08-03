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

public class T implements Reporting, Failing, HamcrestReporting, HamcrestFailing {
    private static final ThreadLocal<T> instance = new ThreadLocal<>();

    private final List<Throwable> events = new ArrayList<>();
    private boolean failed = false;

    /**
     * Manage access to the ThreadLocal instance
     */
    protected T() {
        instance.set(this);
    }

    static T get() {
        final T t = instance.get();
        if (t == null) {
            throw new RuntimeException("Add this to your test class:\n\n@Rule\npublic TRule t = new TRule();\n\n");
        }
        return t;
    }

    @Override
    public void log(Object... args) {
        events.add(trimStackTrace(new Message(join(args))));
    }

    @Override
    public void logf(String format, Object... args) {
        log(String.format(format, args));
    }

    @Override
    public void fail(Object... args) {
        addFailure(join(args));
    }

    @Override
    public void failf(String fmt, Object... args) {
        fail(String.format(fmt, args));
    }

    @Override
    public void terminate(Object... args) throws Exception {
        addFatalError(join(args));
        finish();
    }

    @Override
    public void terminatef(String fmt, Object... args) throws Exception {
        terminate(String.format(fmt, args));
        finish();
    }

    @Override
    public <V> void logWhen(String reason, V value, Matcher<? super V> matcher) {
        if (matcher.matches(value)) {
            get().log(describeMatch(reason, value, matcher));
        }
    }

    @Override
    public <V> void logWhen(V value, Matcher<? super V> matcher) {
        logWhen("", value, matcher);
    }

    @Override
    public <V> void logUnless(String reason, V value, Matcher<? super V> matcher) {
        if (!matcher.matches(value)) {
            get().log(describeMismatch(reason, value, matcher));
        }
    }

    @Override
    public <V> void logUnless(V value, Matcher<? super V> matcher) {
        logUnless("", value, matcher);
    }

    @Override
    public <V> void failWhen(String reason, V value, Matcher<? super V> matcher) {
        if (matcher.matches(value)) {
            fail(describeMatch(reason, value, matcher));
        }
    }

    @Override
    public <V> void failWhen(V value, Matcher<? super V> matcher) {
        failWhen("", value, matcher);
    }

    @Override
    public <V> void failUnless(String reason, V value, Matcher<? super V> matcher) {
        if (!matcher.matches(value)) {
            fail(describeMismatch(reason, value, matcher));
        }
    }

    @Override
    public <V> void failUnless(V value, Matcher<? super V> matcher) {
        failUnless("", value, matcher);
    }

    @Override
    public <V> void terminateWhen(String reason, V value, Matcher<? super V> matcher) throws Exception {
        if (matcher.matches(value)) {
            terminate(describeMatch(reason, value, matcher));
        }
    }

    @Override
    public <V> void terminateWhen(V value, Matcher<? super V> matcher) throws Exception {
        terminateWhen("", value, matcher);
    }

    @Override
    public <V> void terminateUnless(String reason, V value, Matcher<? super V> matcher) throws Exception {
        if (!matcher.matches(value)) {
            terminate(describeMismatch(reason, value, matcher));
        }
    }

    @Override
    public <V> void terminateUnless(V value, Matcher<? super V> matcher) throws Exception {
        terminateUnless("", value, matcher);
    }

    private static <V> Description describeMatch(String reason, V value, Matcher<? super V> matcher) {
        return new StringDescription()
                .appendText(reason)
                .appendText("\nDidn't want: ")
                .appendDescriptionOf(matcher)
                .appendText("\nbut got:     ")
                .appendValue(value);
    }

    private static <V> Description describeMismatch(String reason, V value, Matcher<? super V> matcher) {
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
    protected void finish() throws Exception {
        try {
            if (failed) {
                throw new MultipleFailureException(events);
            }
        } finally {
            instance.remove();
        }
    }

    // TODO (maybe) skip, skipf, skipped (override BlockJUnit4ClassRunner#runChild)

    /**
     * Remove references to ourselves from a stack trace
     *
     * @param cause throwable whose stack trace we mutate
     */
    private Throwable trimStackTrace(Throwable cause) {
        final String thisPackage = getClass().getPackage().getName();
        final String junitAssertClassName = org.junit.Assert.class.getName();
        final List<StackTraceElement> result = new ArrayList<>();

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

    protected void addFailure(Throwable cause) {
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
