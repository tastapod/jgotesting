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
import org.junit.rules.TestRule;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;


public class JGoTest implements TestRule, Reporting<JGoTest>, Checking<JGoTest>, Failing<JGoTest>, Terminating<JGoTest> {
    private static final ThreadLocal<JGoTest> instance = new ThreadLocal<>();

    private final List<Throwable> events = new ArrayList<>();
    private boolean failed = false;

    /**
     * Manage access to the ThreadLocal instance
     */
    public JGoTest() {
        instance.set(this);
    }

    static JGoTest get() {
        final JGoTest test = instance.get();
        if (test == null) {
            throw new RuntimeException("Add this to your test class:\n\n@Rule\npublic JGoTest test = new JGoTest();\n\n");
        }
        return test;
    }

    @Override
    public Statement apply(final Statement base, org.junit.runner.Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } catch (Throwable oops) {
                    addFailure(oops);
                } finally {
                    finish();
                }
            }
        };
    }


    // reporting

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
            get().log(describeMatch(reason, value, matcher));
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
            get().log(describeMismatch(reason, value, matcher));
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
    public <V> JGoTest check(String description, V value, Checker<V> checker) {
        return check(description, checker.check(value));
    }

    @Override
    public <V> JGoTest check(V value, Checker<V> checker) {
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
    public <V> JGoTest checkNot(String description, V value, Checker<V> checker) {
        return checkNot(description, checker.check(value));
    }

    @Override
    public <V> JGoTest checkNot(V value, Checker<V> checker) {
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
     * throws an exception if anything went wrong during the test
     */
    private void finish() throws Exception {
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
