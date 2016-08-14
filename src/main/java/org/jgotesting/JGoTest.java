package org.jgotesting;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jgotesting.events.Failure;
import org.jgotesting.events.FatalFailure;
import org.jgotesting.events.Message;
import org.jgotesting.traits.Checking;
import org.jgotesting.traits.Failing;
import org.jgotesting.traits.Reporting;
import org.jgotesting.traits.Terminating;
import org.junit.runners.model.MultipleFailureException;

import java.util.ArrayList;
import java.util.List;

public class JGoTest implements Reporting<JGoTest>, Checking<JGoTest>, Failing<JGoTest>, Terminating<JGoTest> {
    private final List<Throwable> events = new ArrayList<>();
    private boolean failed = false;

    /**
     * throws an exception if anything went wrong during the test
     */
    protected void finish() throws Exception {
        if (failed) {
            throw new MultipleFailureException(events);
        }
    }

    // Reporting

    @Override
    public JGoTest log(Object... args) {
        events.add(trimStackTrace(new Message(join(args))));
        return this;
    }

    @Override
    public JGoTest logf(String format, Object... args) {
        log(String.format(format, args));
        return this;
    }

    @Override
    public <V> JGoTest logWhen(String reason, V value, Matcher<? super V> matcher) {
        if (matcher.matches(value)) {
            log(describeMatch(reason, value, matcher));
        }
        return this;
    }

    @Override
    public <V> JGoTest logWhen(V value, Matcher<? super V> matcher) {
        logWhen("", value, matcher);
        return this;
    }

    @Override
    public <V> JGoTest logUnless(String reason, V value, Matcher<? super V> matcher) {
        if (!matcher.matches(value)) {
            log(describeMismatch(reason, value, matcher));
        }
        return this;
    }

    @Override
    public <V> JGoTest logUnless(V value, Matcher<? super V> matcher) {
        logUnless("", value, matcher);
        return this;
    }

    // Checking

    @Override
    public JGoTest check(String description, boolean check) {
        if (!check) {
            fail(description);
        }
        return this;
    }

    @Override
    public JGoTest check(boolean check) {
        return check("", check);
    }

    @Override
    public <V> JGoTest check(String description, V value, Checker<? super V> checker) {
        return check(description, checker.check(value));
    }

    @Override
    public <V> JGoTest check(V value, Checker<? super V> checker) {
        return check("", value, checker);
    }

    @Override
    public <V> JGoTest check(String description, V value, Matcher<? super V> matcher) {
        if (!matcher.matches(value)) {
            fail(describeMismatch(description, value, matcher));
        }
        return this;
    }

    @Override
    public <V> JGoTest check(V value, Matcher<? super V> matcher) {
        return check("", value, matcher);
    }

    @Override
    public JGoTest checkNot(String description, boolean check) {
        return check(description, !check);
    }

    @Override
    public JGoTest checkNot(boolean check) {
        return checkNot("", check);
    }

    @Override
    public <V> JGoTest checkNot(String description, V value, Checker<? super V> checker) {
        return checkNot(description, checker.check(value));
    }

    @Override
    public <V> JGoTest checkNot(V value, Checker<? super V> checker) {
        return checkNot("", value, checker);
    }

    @Override
    public <V> JGoTest checkNot(String description, V value, Matcher<? super V> matcher) {
        if (matcher.matches(value)) {
            fail(describeMatch(description, value, matcher));
        }
        return this;
    }

    @Override
    public <V> JGoTest checkNot(V value, Matcher<? super V> matcher) {
        return checkNot("", value, matcher);
    }

    // Failing

    @Override
    public JGoTest fail(Object... args) {
        addFailure(join(args));
        return this;
    }

    @Override
    public JGoTest failf(String fmt, Object... args) {
        fail(String.format(fmt, args));
        return this;
    }

    // Terminating

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
    public JGoTest terminateWhen(String description, boolean check) throws Exception {
        if (check) {
            terminate(description);
        }
        return this;
    }

    @Override
    public JGoTest terminateWhen(boolean check) throws Exception {
        return terminateWhen("", check);
    }

    @Override
    public <V> JGoTest terminateWhen(V value, Checker<? super V> checker) throws Exception {
        return terminateWhen(checker.check(value));
    }

    @Override
    public <V> JGoTest terminateWhen(String description, V value, Checker<? super V> checker) throws Exception {
        return terminateWhen(description, checker.check(value));
    }

    @Override
    public <V> JGoTest terminateWhen(String reason, V value, Matcher<? super V> matcher) throws Exception {
        if (matcher.matches(value)) {
            terminate(describeMatch(reason, value, matcher));
        }
        return this;
    }

    @Override
    public <V> JGoTest terminateWhen(V value, Matcher<? super V> matcher) throws Exception {
        terminateWhen("", value, matcher);
        return this;
    }

    @Override
    public JGoTest terminateUnless(String description, boolean check) throws Exception {
        if (!check) {
            terminate(description);
        }
        return this;
    }

    @Override
    public JGoTest terminateUnless(boolean check) throws Exception {
        return terminateUnless("", check);
    }

    @Override
    public <V> JGoTest terminateUnless(V value, Checker<? super V> checker) throws Exception {
        return terminateUnless(checker.check(value));
    }

    @Override
    public <V> JGoTest terminateUnless(String description, V value, Checker<? super V> checker) throws Exception {
        return terminateUnless(description, checker.check(value));
    }

    @Override
    public <V> JGoTest terminateUnless(String reason, V value, Matcher<? super V> matcher) throws Exception {
        if (!matcher.matches(value)) {
            terminate(describeMismatch(reason, value, matcher));
        }
        return this;
    }

    @Override
    public <V> JGoTest terminateUnless(V value, Matcher<? super V> matcher) throws Exception {
        terminateUnless("", value, matcher);
        return this;
    }

    /**
     * Remove references to ourselves from a stack trace
     *
     * @param cause throwable whose stack trace we mutate
     */
    private Throwable trimStackTrace(Throwable cause) {
        final String thisPackage = JGoTest.class.getPackage().getName();
        final String junitAssertClassName = org.junit.Assert.class.getName();
        final List<StackTraceElement> result = new ArrayList<>();

        for (StackTraceElement element : cause.getStackTrace()) {
            String className = element.getClassName();
            String packageName = className.substring(0, className.lastIndexOf('.'));

            if (!packageName.startsWith(thisPackage) && !className.equals(junitAssertClassName)) {
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

    private static <V> Description describeMatch(String description, V value, Matcher<? super V> matcher) {
        return new StringDescription()
                .appendText(description)
                .appendText("\nDidn't want: ")
                .appendDescriptionOf(matcher)
                .appendText("\nbut got:     ")
                .appendValue(value);
    }

    private static <V> Description describeMismatch(String description, V value, Matcher<? super V> matcher) {
        return new StringDescription()
                .appendText(description)
                .appendText("\nWanted:  ")
                .appendDescriptionOf(matcher)
                .appendText("\nbut got: ")
                .appendValue(value);
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
