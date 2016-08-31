package org.jgotesting;

import org.hamcrest.Matcher;

@SuppressWarnings("unused")
public class Testing {
    private static final ThreadLocal<JGoTest> instance = new ThreadLocal<>();

    public static void log(Object... args) {
        test().log(args);
    }

    public static void logf(String format, Object... args) {
        test().logf(format, args);
    }

    public static void fail(Object... args) {
        test().fail(args);
    }

    public static void failf(String fmt, Object... args) {
        test().failf(fmt, args);
    }

    public static void terminate(Object... args) throws Exception {
        test().terminate(args);
    }

    public static void failfNow(String fmt, Object... args) throws Exception {
        test().terminatef(fmt, args);
    }

    public static void _addFailure(Throwable cause) {
        test().addFailure(cause);
    }

    // Hamcrest matcher methods

    public static <V> void logWhen(String reason, V value, Matcher<? super V> matcher) {
        test().logWhen(reason, value, matcher);
    }

    public static <V> void logWhen(V value, Matcher<? super V> matcher) {
        test().logWhen(value, matcher);
    }

    public static <V> void logUnless(String reason, V value, Matcher<? super V> matcher) {
        test().logUnless(reason, value, matcher);
    }

    public static <V> void logUnless(V value, Matcher<? super V> matcher) {
        test().logUnless(value, matcher);
    }

    public static <V> void failWhen(String reason, V value, Matcher<? super V> matcher) {
        test().checkNot(reason, value, matcher);
    }

    public static <V> void failWhen(V value, Matcher<? super V> matcher) {
        test().checkNot(value, matcher);
    }

    public static <V> void failUnless(String reason, V value, Matcher<? super V> matcher) {
        test().check(reason, value, matcher);
    }

    public static <V> void failUnless(V value, Matcher<? super V> matcher) {
        test().check(value, matcher);
    }

    public static <V> void terminateWhen(String reason, V value, Matcher<? super V> matcher) throws Exception {
        test().terminateIf(reason, value, matcher);
    }

    public static <V> void terminateWhen(V value, Matcher<? super V> matcher) throws Exception {
        test().terminateIf(value, matcher);
    }

    public static <V> void terminateUnless(String reason, V value, Matcher<? super V> matcher) throws Exception {
        test().terminateUnless(reason, value, matcher);
    }

    public static <V> void terminateUnless(V value, Matcher<? super V> matcher) throws Exception {
        test().terminateUnless(value, matcher);
    }

    // Access to ThreadLocal instance

    public static void setInstance(JGoTest test) {
        instance.set(test);
    }

    public static void removeInstance() {
        instance.remove();
    }

    private static JGoTest test() {
        final JGoTest test = instance.get();
        if (test == null) {
            throw new RuntimeException("Add this to your test class:\n\n@Rule\npublic JGoTestRule test = new JGoTestRule();\n\n");
        }
        return test;
    }
}
